package com.ssdut411.app.questionanswer.core;

import android.content.Context;

import com.ssdut411.app.questionanswer.api.Api;
import com.ssdut411.app.questionanswer.api.ApiCallbackListener;
import com.ssdut411.app.questionanswer.api.ApiConfig;
import com.ssdut411.app.questionanswer.api.ApiImpl;
import com.ssdut411.app.questionanswer.model.Req.AddClazzReq;
import com.ssdut411.app.questionanswer.model.Req.AddSchoolReq;
import com.ssdut411.app.questionanswer.model.Req.CollectionReq;
import com.ssdut411.app.questionanswer.model.Req.FinishInfoReq;
import com.ssdut411.app.questionanswer.model.Req.ForgetPasswordReq;
import com.ssdut411.app.questionanswer.model.Req.GetClazzDetailReq;
import com.ssdut411.app.questionanswer.model.Req.GetClazzReq;
import com.ssdut411.app.questionanswer.model.Req.GetCollectionsReq;
import com.ssdut411.app.questionanswer.model.Req.GetErrorsReq;
import com.ssdut411.app.questionanswer.model.Req.GetHeadReq;
import com.ssdut411.app.questionanswer.model.Req.GetHomeworkByIdReq;
import com.ssdut411.app.questionanswer.model.Req.GetInfoReq;
import com.ssdut411.app.questionanswer.model.Req.GetMyTestReq;
import com.ssdut411.app.questionanswer.model.Req.GetNewTestReq;
import com.ssdut411.app.questionanswer.model.Req.GetSchoolReq;
import com.ssdut411.app.questionanswer.model.Req.GetSelfTestByIdReq;
import com.ssdut411.app.questionanswer.model.Req.GetStudentAnalysisReq;
import com.ssdut411.app.questionanswer.model.Req.GetStudentHomeworkByIdReq;
import com.ssdut411.app.questionanswer.model.Req.GetStudentHomeworkReq;
import com.ssdut411.app.questionanswer.model.Req.GetTeacherHomeworkReq;
import com.ssdut411.app.questionanswer.model.Req.GetTestSitesReq;
import com.ssdut411.app.questionanswer.model.Req.LoginReq;
import com.ssdut411.app.questionanswer.model.Req.PostHomeworkReq;
import com.ssdut411.app.questionanswer.model.Req.RegisterReq;
import com.ssdut411.app.questionanswer.model.Req.SearchPersonReq;
import com.ssdut411.app.questionanswer.model.Req.SubmitStudentHomeworkReq;
import com.ssdut411.app.questionanswer.model.Req.SubmitStudentReq;
import com.ssdut411.app.questionanswer.model.Req.SubmitTestReq;
import com.ssdut411.app.questionanswer.model.Req.UnCollectionReq;
import com.ssdut411.app.questionanswer.model.Resp.AddClazzResp;
import com.ssdut411.app.questionanswer.model.Resp.AddSchoolResp;
import com.ssdut411.app.questionanswer.model.Resp.CollectionResp;
import com.ssdut411.app.questionanswer.model.Resp.FinishInfoResp;
import com.ssdut411.app.questionanswer.model.Resp.ForgetPasswordResp;
import com.ssdut411.app.questionanswer.model.Resp.GetClazzDetailResp;
import com.ssdut411.app.questionanswer.model.Resp.GetClazzResp;
import com.ssdut411.app.questionanswer.model.Resp.GetCollectionsResp;
import com.ssdut411.app.questionanswer.model.Resp.GetErrorsResp;
import com.ssdut411.app.questionanswer.model.Resp.GetHeadResp;
import com.ssdut411.app.questionanswer.model.Resp.GetHomeworkByIdResp;
import com.ssdut411.app.questionanswer.model.Resp.GetInfoResp;
import com.ssdut411.app.questionanswer.model.Resp.GetMyTestResp;
import com.ssdut411.app.questionanswer.model.Resp.GetNewTestResp;
import com.ssdut411.app.questionanswer.model.Resp.GetSchoolResp;
import com.ssdut411.app.questionanswer.model.Resp.GetSelfTestByIdResp;
import com.ssdut411.app.questionanswer.model.Resp.GetStudentAnalysisResp;
import com.ssdut411.app.questionanswer.model.Resp.GetStudentHomeworkByIdResp;
import com.ssdut411.app.questionanswer.model.Resp.GetStudentHomeworkResp;
import com.ssdut411.app.questionanswer.model.Resp.GetTeacherHomeworkResp;
import com.ssdut411.app.questionanswer.model.Resp.GetTestSitesResp;
import com.ssdut411.app.questionanswer.model.Resp.LoginResp;
import com.ssdut411.app.questionanswer.model.Resp.PostHomeworkResp;
import com.ssdut411.app.questionanswer.model.Resp.RegisterResp;
import com.ssdut411.app.questionanswer.model.Resp.SearchPersonResp;
import com.ssdut411.app.questionanswer.model.Resp.SubmitStudentHomeworkResp;
import com.ssdut411.app.questionanswer.model.Resp.SubmitStudentResp;
import com.ssdut411.app.questionanswer.model.Resp.SubmitTestResp;
import com.ssdut411.app.questionanswer.model.Resp.UnCollectionResp;
import com.ssdut411.app.questionanswer.utils.GsonUtils;

/**
 * AppAction接口的实现
 * <p/>
 * Created by yao_han on 2015/11/24.
 */
public class AppActionImpl implements AppAction {
    private Context context;
    private Api api;

    public AppActionImpl(Context context) {
        this.context = context;
        this.api = new ApiImpl();
    }

    @Override
    public void login(LoginReq req, final ActionCallbackListener<LoginResp> listener) {
        String url = ApiConfig.BASE_URL + "/login";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.login(url, reqJson, context, new ApiCallbackListener<LoginResp>() {
            @Override
            public void onSuccess(LoginResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void forgetPassword(ForgetPasswordReq req, final ActionCallbackListener<ForgetPasswordResp> listener) {
        String url = ApiConfig.BASE_URL + "/forgetPassword";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.forgetPassword(url, reqJson, context, new ApiCallbackListener<ForgetPasswordResp>() {
            @Override
            public void onSuccess(ForgetPasswordResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void register(RegisterReq req, final ActionCallbackListener<RegisterResp> listener) {
        String url = ApiConfig.BASE_URL + "/register";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.register(url, reqJson, context, new ApiCallbackListener<RegisterResp>() {
            @Override
            public void onSuccess(RegisterResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void finishInfo(FinishInfoReq req, final ActionCallbackListener<FinishInfoResp> listener) {
        String url = ApiConfig.BASE_URL + "/finishInfo";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.finishInfo(url, reqJson, context, new ApiCallbackListener<FinishInfoResp>() {
            @Override
            public void onSuccess(FinishInfoResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getInfo(GetInfoReq req, final ActionCallbackListener<GetInfoResp> listener) {
        String url = ApiConfig.BASE_URL + "/getInfo";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.getInfo(url, reqJson, context, new ApiCallbackListener<GetInfoResp>() {
            @Override
            public void onSuccess(GetInfoResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getSchoolList(GetSchoolReq req, final ActionCallbackListener<GetSchoolResp> listener) {
        String url = ApiConfig.BASE_URL + "/getSchoolList";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.getSchoolList(url, reqJson, context, new ApiCallbackListener<GetSchoolResp>() {
            @Override
            public void onSuccess(GetSchoolResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void addSchool(AddSchoolReq req, final ActionCallbackListener<AddSchoolResp> listener) {
        String url = ApiConfig.BASE_URL + "/addSchool";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.addSchool(url, reqJson, context, new ApiCallbackListener<AddSchoolResp>() {
            @Override
            public void onSuccess(AddSchoolResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getClazzList(GetClazzReq req, final ActionCallbackListener<GetClazzResp> listener) {
        String url = ApiConfig.BASE_URL + "/getClazzList";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.getClazzList(url, reqJson, context, new ApiCallbackListener<GetClazzResp>() {
            @Override
            public void onSuccess(GetClazzResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void addClazz(AddClazzReq req, final ActionCallbackListener<AddClazzResp> listener) {
        String url = ApiConfig.BASE_URL + "/addClazz";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.addClazz(url, reqJson, context, new ApiCallbackListener<AddClazzResp>() {
            @Override
            public void onSuccess(AddClazzResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getTestSites(GetTestSitesReq req, final ActionCallbackListener<GetTestSitesResp> listener) {
        String url = ApiConfig.BASE_URL + "/getTestSites";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.getTestSites(url, reqJson, context, new ApiCallbackListener<GetTestSitesResp>() {
            @Override
            public void onSuccess(GetTestSitesResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getNewTest(GetNewTestReq req, final ActionCallbackListener<GetNewTestResp> listener) {
        String url = ApiConfig.BASE_URL + "/getNewTest";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.getNewTest(url, reqJson, context, new ApiCallbackListener<GetNewTestResp>() {
            @Override
            public void onSuccess(GetNewTestResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getMyTest(GetMyTestReq req, final ActionCallbackListener<GetMyTestResp> listener) {
        String url = ApiConfig.BASE_URL + "/getMyTest";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.getMyTest(url, reqJson, context, new ApiCallbackListener<GetMyTestResp>() {
            @Override
            public void onSuccess(GetMyTestResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getSelfTestById(GetSelfTestByIdReq req, final ActionCallbackListener<GetSelfTestByIdResp> listener) {
        String url = ApiConfig.BASE_URL + "/getSelfTestById";
        String reqJson = GsonUtils.gsonToJsonString(req);
        api.getSelfTestById(url, reqJson, context, new ApiCallbackListener<GetSelfTestByIdResp>() {
            @Override
            public void onSuccess(GetSelfTestByIdResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void submitTest(SubmitTestReq req, final ActionCallbackListener<SubmitTestResp> listener) {
        String url = ApiConfig.BASE_URL + "/submitTest";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.submitTest(url, reqJson, context, new ApiCallbackListener<SubmitTestResp>() {
            @Override
            public void onSuccess(SubmitTestResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void collection(CollectionReq req, final ActionCallbackListener<CollectionResp> listener) {
        String url = ApiConfig.BASE_URL + "/collection";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.collection(url, reqJson, context, new ApiCallbackListener<CollectionResp>() {
            @Override
            public void onSuccess(CollectionResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void unCollection(UnCollectionReq req, final ActionCallbackListener<UnCollectionResp> listener) {
        String url = ApiConfig.BASE_URL + "/unCollection";
        String reqJson = GsonUtils.gsonToJsonString(req);
        api.unCollection(url, reqJson, context, new ApiCallbackListener<UnCollectionResp>() {
            @Override
            public void onSuccess(UnCollectionResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getErrors(GetErrorsReq req, final ActionCallbackListener<GetErrorsResp> listener) {
        String url = ApiConfig.BASE_URL + "/getErrors";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.getErrors(url, reqJson, context, new ApiCallbackListener<GetErrorsResp>() {
            @Override
            public void onSuccess(GetErrorsResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getCollections(GetCollectionsReq req, final ActionCallbackListener<GetCollectionsResp> listener) {
        String url = ApiConfig.BASE_URL + "/getCollections";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.getCollections(url, reqJson, context, new ApiCallbackListener<GetCollectionsResp>() {
            @Override
            public void onSuccess(GetCollectionsResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void searchPerson(SearchPersonReq req, final ActionCallbackListener<SearchPersonResp> listener) {
        String url = ApiConfig.BASE_URL + "/searchPerson";
        String reqJson = GsonUtils.gsonToJsonString(req);

        api.searchPerson(url, reqJson, context, new ApiCallbackListener<SearchPersonResp>() {
            @Override
            public void onSuccess(SearchPersonResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getClazzDetail(GetClazzDetailReq req, final ActionCallbackListener<GetClazzDetailResp> listener) {
        String url = ApiConfig.BASE_URL + "/getClazzDetail";
        String reqJson = GsonUtils.gsonToJsonString(req);
        api.getClazzDetail(url, reqJson, context, new ApiCallbackListener<GetClazzDetailResp>() {
            @Override
            public void onSuccess(GetClazzDetailResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void postHomework(PostHomeworkReq req, final ActionCallbackListener<PostHomeworkResp> listener) {
        String url = ApiConfig.BASE_URL + "/postHomework";
        String reqJson = GsonUtils.gsonToJsonString(req);
        api.postHomework(url, reqJson, PostHomeworkResp.class, new ApiCallbackListener<PostHomeworkResp>() {
            @Override
            public void onSuccess(PostHomeworkResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getTeacherHomework(GetTeacherHomeworkReq req, final ActionCallbackListener<GetTeacherHomeworkResp> listener) {
        String url = ApiConfig.BASE_URL + "/getTeacherHomework";
        String reqJson = GsonUtils.gsonToJsonString(req);
        api.getTeacherHomework(url, reqJson, GetTeacherHomeworkResp.class, new ApiCallbackListener<GetTeacherHomeworkResp>() {
            @Override
            public void onSuccess(GetTeacherHomeworkResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getHomeworkById(GetHomeworkByIdReq req, final ActionCallbackListener<GetHomeworkByIdResp> listener) {
        String url = ApiConfig.BASE_URL + "/getHomeworkById";
        String reqJson = GsonUtils.gsonToJsonString(req);
        api.getHomeworkById(url, reqJson, GetHomeworkByIdResp.class, new ApiCallbackListener<GetHomeworkByIdResp>() {
            @Override
            public void onSuccess(GetHomeworkByIdResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void submitStudent(SubmitStudentReq req, final ActionCallbackListener<SubmitStudentResp> listener) {
        String url = ApiConfig.BASE_URL + "/submitStudent";
        String reqJson = GsonUtils.gsonToJsonString(req);
        api.submitStudent(url, reqJson, SubmitStudentResp.class, new ApiCallbackListener<SubmitStudentResp>() {
            @Override
            public void onSuccess(SubmitStudentResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getStudentHomework(GetStudentHomeworkReq req, final ActionCallbackListener<GetStudentHomeworkResp> listener) {
        String url = ApiConfig.BASE_URL + "/getStudentHomework";
        String reqJson = GsonUtils.gsonToJsonString(req);
        api.getStudentHomework(url, reqJson, GetStudentHomeworkResp.class, new ApiCallbackListener<GetStudentHomeworkResp>() {
            @Override
            public void onSuccess(GetStudentHomeworkResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getStudentHomeworkById(GetStudentHomeworkByIdReq req, final ActionCallbackListener<GetStudentHomeworkByIdResp> listener) {
        String url = ApiConfig.BASE_URL + "/getStudentHomeworkById";
        String reqJson = GsonUtils.gsonToJsonString(req);
        api.getStudentHomeworkById(url, reqJson, GetHomeworkByIdResp.class, new ApiCallbackListener<GetStudentHomeworkByIdResp>() {
            @Override
            public void onSuccess(GetStudentHomeworkByIdResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void submitStudentHomework(SubmitStudentHomeworkReq req, final ActionCallbackListener<SubmitStudentHomeworkResp> listener) {
        String url = ApiConfig.BASE_URL + "/submitStudentHomework";
        String reqJson = GsonUtils.gsonToJsonString(req);
        api.submitStudentHomework(url, reqJson, SubmitStudentHomeworkResp.class, new ApiCallbackListener<SubmitStudentHomeworkResp>() {
            @Override
            public void onSuccess(SubmitStudentHomeworkResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getStudentAnalysis(GetStudentAnalysisReq req, final ActionCallbackListener<GetStudentAnalysisResp> listener) {
        String url = ApiConfig.BASE_URL + "/getStudentAnalysis";
        String reqJson = GsonUtils.gsonToJsonString(req);
        api.getStudentAnalysis(url, reqJson, GetStudentAnalysisResp.class, new ApiCallbackListener<GetStudentAnalysisResp>() {
            @Override
            public void onSuccess(GetStudentAnalysisResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

    @Override
    public void getHead(GetHeadReq req, final ActionCallbackListener<GetHeadResp> listener) {
        String url = ApiConfig.BASE_URL + "/getHead";
        String reqJson = GsonUtils.gsonToJsonString(req);
        api.getHead(url, reqJson, GetHeadResp.class, new ApiCallbackListener<GetHeadResp>() {
            @Override
            public void onSuccess(GetHeadResp data) {
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(String message) {
                listener.onFailure(message);
            }
        });
    }

}



