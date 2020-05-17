package di;

import api.*;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = ApiModule.class)
public interface ApiComponent {
    GroupApi provideGroupApi();
    HumanApi provideHumanApi();
    SubjectApi provideSubjectApi();
    MarkApi provideMarkApi();
    AuthApi provideAuthApi();
}
