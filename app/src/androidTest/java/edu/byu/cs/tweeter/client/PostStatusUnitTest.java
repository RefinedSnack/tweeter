package edu.byu.cs.tweeter.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.model.service.MainService;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusUnitTest
{
    MainService mainService;
    MainPresenter mainPresenter;
    MainPresenter.MainView mainView;

    @BeforeEach
    void setupBeforeEach()
    {
        mainService = Mockito.mock(MainService.class);
        mainView = Mockito.mock(MainPresenter.MainView.class);
        mainPresenter = Mockito.spy(new MainPresenter(mainView));

        Mockito.when(mainPresenter.mainServiceFactory()).thenReturn(mainService);
    }

    @Test
    void SuccessfullyPosted()
    {
        AuthToken ourToken = new AuthToken();
        String statusContents = "Dummy contents";
        Answer answer = invocationOnMock ->
        {
            Assertions.assertEquals(ourToken, invocationOnMock.getArgument(0));
            Assertions.assertEquals(statusContents, ((Status)invocationOnMock.getArgument(1)).getPost());
            MainService.SimpleServiceObserver processList = invocationOnMock.getArgument(2);
            processList.handleSuccess();
            return null;
        };

        Mockito.doAnswer(answer).when(mainService).postStatus(
                Mockito.any(AuthToken.class),
                Mockito.any(Status.class),
                Mockito.any(MainService.SimpleServiceObserver.class)
        );


        mainPresenter.postStatus(ourToken, statusContents);

        Mockito.verify(mainView).displayPostingInfoMessage("Posting Status...");
        Mockito.verify(mainService).postStatus(
                Mockito.any(AuthToken.class),
                Mockito.any(Status.class),
                Mockito.any(MainService.SimpleServiceObserver.class)
        );

        Mockito.verify(mainView).displayInfoMessage("Successfully Posted!");
    }

    @Test
    void FailedToPost()
    {
        Answer answer = invocationOnMock ->
        {
            MainService.SimpleServiceObserver processList = invocationOnMock.getArgument(2);
            processList.handleFailure("Some Failure Reason");
            return null;
        };

        Mockito.doAnswer(answer).when(mainService).postStatus(
                Mockito.any(AuthToken.class),
                Mockito.any(Status.class),
                Mockito.any(MainService.SimpleServiceObserver.class)
        );

        mainPresenter.postStatus(new AuthToken(), "");

        Mockito.verify(mainView).displayPostingInfoMessage("Posting Status...");
        Mockito.verify(mainService).postStatus(
                Mockito.any(AuthToken.class),
                Mockito.any(Status.class),
                Mockito.any(MainService.SimpleServiceObserver.class)
        );

        Mockito.verify(mainView).displayInfoMessage("Failed to post status: Some Failure Reason");
    }

    @Test
    void PostThrewException()
    {
        Answer answer = invocationOnMock ->
        {
            MainService.SimpleServiceObserver processList = invocationOnMock.getArgument(2);
            processList.handleException(new Exception("Some Exception"));
            return null;
        };

        Mockito.doAnswer(answer).when(mainService).postStatus(
                Mockito.any(AuthToken.class),
                Mockito.any(Status.class),
                Mockito.any(MainService.SimpleServiceObserver.class)
        );

        mainPresenter.postStatus(new AuthToken(), "");

        Mockito.verify(mainView).displayPostingInfoMessage("Posting Status...");
        Mockito.verify(mainService).postStatus(
                Mockito.any(AuthToken.class),
                Mockito.any(Status.class),
                Mockito.any(MainService.SimpleServiceObserver.class)
        );

        Mockito.verify(mainView).displayInfoMessage("Failed to post status because of exception: Some Exception");
    }


}
