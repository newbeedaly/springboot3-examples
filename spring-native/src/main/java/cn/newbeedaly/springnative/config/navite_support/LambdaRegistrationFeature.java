package cn.newbeedaly.springnative.config.navite_support;

import cn.newbeedaly.springnative.SpringNativeApplication;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeSerialization;

public class LambdaRegistrationFeature implements Feature {

    @Override
    public void duringSetup(DuringSetupAccess access) {
        RuntimeSerialization.registerLambdaCapturingClass(SpringNativeApplication.class);
    }

}
