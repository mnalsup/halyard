/*
 * Copyright 2017 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.netflix.spinnaker.halyard.cli.command.v1.deploy;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.netflix.spinnaker.halyard.cli.command.v1.config.AbstractConfigCommand;
import com.netflix.spinnaker.halyard.cli.services.v1.Daemon;
import com.netflix.spinnaker.halyard.cli.services.v1.OperationHandler;
import com.netflix.spinnaker.halyard.cli.ui.v1.AnsiFormatUtils;
import com.netflix.spinnaker.halyard.deploy.spinnaker.v1.RunningServiceDetails;
import lombok.AccessLevel;
import lombok.Getter;

@Parameters(separators = "=")
public class DetailsDeployCommand extends AbstractConfigCommand {
  @Getter(AccessLevel.PUBLIC)
  private String commandName = "details";

  @Getter(AccessLevel.PUBLIC)
  private String shortDescription = "Get details about your currently deployed Spinnaker installation.";

  @Parameter(
      names = "--service-name",
      required = true,
      description = "The name of the service to inspect."
  )
  private String serviceName;

  @Override
  protected void executeThis() {
    String deploymentName = getCurrentDeployment();

    new OperationHandler<RunningServiceDetails>()
        .setFailureMesssage("Failed load service details for service " + serviceName + ".")
        .setOperation(Daemon.getServiceDetails(deploymentName, serviceName, !noValidate))
        .setFormat(AnsiFormatUtils.Format.STRING)
        .setUserFormatted(true)
        .get();
  }
}
