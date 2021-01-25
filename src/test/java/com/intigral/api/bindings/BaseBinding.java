package com.intigral.api.bindings;

import com.intigral.api.steps.HealthCheckSteps;
import net.thucydides.core.annotations.Steps;

abstract class BaseBinding {

    @Steps
    HealthCheckSteps healthCheckSteps;
}
