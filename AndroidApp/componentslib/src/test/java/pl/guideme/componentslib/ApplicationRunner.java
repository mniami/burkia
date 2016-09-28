package pl.guideme.componentslib;

import android.app.Application;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.bytecode.InstrumentationConfiguration;

public class ApplicationRunner extends RobolectricTestRunner {

    public ApplicationRunner(Class<?> testClass)
            throws InitializationError {
        super(testClass);
    }

    @Override
    public InstrumentationConfiguration createClassLoaderConfig(Config config) {
        InstrumentationConfiguration.Builder builder = InstrumentationConfiguration.newBuilder();
        builder.addInstrumentedClass(Application.class.getName());
        return builder.build();
    }
}