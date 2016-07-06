package com.ssdut411.app.questionanswer.api;


import com.ssdut411.app.questionanswer.api.volley.VolleyUtil;
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

/**
 * Api接口实现类
 *
 * Created by yao_han on 2015/11/24.
 */
public class ApiImpl implements Api{

    @Override
    public void login(String url, String reqJson, Object tag, ApiCallbackListener<LoginResp> listener) {
        VolleyUtil.doPost(url, reqJson, LoginResp.class, tag, listener);
    }

    @Override
    public void forgetPassword(String url, String reqJson, Object tag, ApiCallbackListener<ForgetPasswordResp> listener) {
        VolleyUtil.doPost(url, reqJson, ForgetPasswordResp.class, tag, listener);
    }

    @Override
    public void register(String url, String reqJson, Object tag, ApiCallbackListener<RegisterResp> listener) {
        VolleyUtil.doPost(url, reqJson, RegisterResp.class, tag, listener);
    }

    @Override
    public void finishInfo(String url, String reqJson, Object tag, ApiCallbackListener<FinishInfoResp> listener) {
        VolleyUtil.doPost(url, reqJson, FinishInfoResp.class, tag, listener);
    }

    @Override
    public void getInfo(String url, String reqJson, Object tag, ApiCallbackListener<GetInfoResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetInfoResp.class, tag, listener);
    }

    @Override
    public void getSchoolList(String url, String reqJson, Object tag, ApiCallbackListener<GetSchoolResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetSchoolResp.class, tag, listener);
    }

    @Override
    public void addSchool(String url, String reqJson, Object tag, ApiCallbackListener<AddSchoolResp> listener) {
        VolleyUtil.doPost(url, reqJson, AddSchoolResp.class, tag, listener);
    }

    @Override
    public void getClazzList(String url, String reqJson, Object tag, ApiCallbackListener<GetClazzResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetClazzResp.class, tag, listener);
    }

    @Override
    public void addClazz(String url, String reqJson, Object tag, ApiCallbackListener<AddClazzResp> listener) {
        VolleyUtil.doPost(url, reqJson, AddClazzResp.class, tag, listener);
    }

    @Override
    public void getTestSites(String url, String reqJson, Object tag, ApiCallbackListener<GetTestSitesResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetTestSitesResp.class, tag, listener);
    }

    @Override
    public void getNewTest(String url, String reqJson, Object tag, ApiCallbackListener<GetNewTestResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetNewTestResp.class, tag, listener);
    }

    @Override
    public void getMyTest(String url, String reqJson, Object tag, ApiCallbackListener<GetMyTestResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetMyTestResp.class, tag, listener);
    }

    @Override
    public void getSelfTestById(String url, String reqJson, Object tag, ApiCallbackListener<GetSelfTestByIdResp> listener) {
        VolleyUtil.doPost(url,reqJson, GetSelfTestByIdResp.class,tag, listener);
    }

    @Override
    public void submitTest(String url, String reqJson, Object tag, ApiCallbackListener<SubmitTestResp> listener) {
        VolleyUtil.doPost(url, reqJson, SubmitTestResp.class, tag, listener);
    }

    @Override
    public void collection(String url, String reqJson, Object tag, ApiCallbackListener<CollectionResp> listener) {
        VolleyUtil.doPost(url, reqJson, CollectionResp.class, tag, listener);
    }

    @Override
    public void unCollection(String url, String reqJson, Object tag, ApiCallbackListener<UnCollectionResp> listener) {
        VolleyUtil.doPost(url, reqJson, UnCollectionResp.class, tag, listener);
    }

    @Override
    public void getErrors(String url, String reqJson, Object tag, ApiCallbackListener<GetErrorsResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetErrorsResp.class, tag, listener);
    }

    @Override
    public void getCollections(String url, String reqJson, Object tag, ApiCallbackListener<GetCollectionsResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetCollectionsResp.class, tag, listener);
    }

    @Override
    public void searchPerson(String url, String reqJson, Object tag, ApiCallbackListener<SearchPersonResp> listener) {
        VolleyUtil.doPost(url, reqJson, SearchPersonResp.class, tag, listener);
    }

    @Override
    public void getClazzDetail(String url, String reqJson, Object tag, ApiCallbackListener<GetClazzDetailResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetClazzDetailResp.class, tag, listener);
    }

    @Override
    public void postHomework(String url, String reqJson, Object tag, ApiCallbackListener<PostHomeworkResp> listener) {
        VolleyUtil.doPost(url, reqJson, PostHomeworkResp.class, tag, listener);
    }

    @Override
    public void getTeacherHomework(String url, String reqJson, Object tag, ApiCallbackListener<GetTeacherHomeworkResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetTeacherHomeworkResp.class, tag, listener);
    }

    @Override
    public void getHomeworkById(String url, String reqJson, Object tag, ApiCallbackListener<GetHomeworkByIdResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetHomeworkByIdResp.class, tag, listener);
    }

    @Override
    public void submitStudent(String url, String reqJson, Object tag, ApiCallbackListener<SubmitStudentResp> listener) {
        VolleyUtil.doPost(url, reqJson, SubmitStudentResp.class, tag, listener);
    }

    @Override
    public void getStudentHomework(String url, String reqJson, Object tag, ApiCallbackListener<GetStudentHomeworkResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetStudentHomeworkResp.class, tag, listener);
    }

    @Override
    public void getStudentHomeworkById(String url, String reqJson, Object tag, ApiCallbackListener<GetStudentHomeworkByIdResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetStudentHomeworkByIdResp.class, tag, listener);
    }

    @Override
    public void submitStudentHomework(String url, String reqJson, Object tag, ApiCallbackListener<SubmitStudentHomeworkResp> listener) {
        VolleyUtil.doPost(url, reqJson, SubmitStudentHomeworkResp.class, tag, listener);
    }

    @Override
    public void getStudentAnalysis(String url, String reqJson, Object tag, ApiCallbackListener<GetStudentAnalysisResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetStudentAnalysisResp.class, tag, listener);
    }

    @Override
    public void getHead(String url, String reqJson, Object tag, ApiCallbackListener<GetHeadResp> listener) {
        VolleyUtil.doPost(url, reqJson, GetHeadResp.class, tag, listener);
    }

}
