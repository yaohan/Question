package com.ssdut411.app.questionanswer.api;

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
 * Api接口
 * <p/>
 * Created by yao_han on 2015/11/24.
 */
public interface Api {
    public void login(String url, String reqJson, Object tag, ApiCallbackListener<LoginResp> listener);
    public void forgetPassword(String url, String reqJson, Object tag, ApiCallbackListener<ForgetPasswordResp> listener);
    public void register(String url, String reqJson, Object tag, ApiCallbackListener<RegisterResp> listener);
    public void finishInfo(String url, String reqJson, Object tag, ApiCallbackListener<FinishInfoResp> listener);
    public void getInfo(String url, String reqJson, Object tag, ApiCallbackListener<GetInfoResp> listener);
    public void getSchoolList(String url, String reqJson, Object tag, ApiCallbackListener<GetSchoolResp> listener);
    public void addSchool(String url, String reqJson, Object tag, ApiCallbackListener<AddSchoolResp> listener);
    public void getClazzList(String url, String reqJson, Object tag, ApiCallbackListener<GetClazzResp> listener);
    public void addClazz(String url, String reqJson, Object tag, ApiCallbackListener<AddClazzResp> listener);
    public void getTestSites(String url, String reqJson, Object tag, ApiCallbackListener<GetTestSitesResp> listener);
    public void getNewTest(String url, String reqJson, Object tag, ApiCallbackListener<GetNewTestResp> listener);
    public void getMyTest(String url, String reqJson, Object tag, ApiCallbackListener<GetMyTestResp> listener);
    public void getSelfTestById(String url, String reqJson, Object tag, ApiCallbackListener<GetSelfTestByIdResp> listener);
    public void submitTest(String url, String reqJson, Object tag, ApiCallbackListener<SubmitTestResp> listener);
    public void collection(String url, String reqJson, Object tag, ApiCallbackListener<CollectionResp> listener);
    public void unCollection(String url, String reqJson, Object tag, ApiCallbackListener<UnCollectionResp> listener);
    public void getErrors(String url, String reqJson, Object tag, ApiCallbackListener<GetErrorsResp> listener);
    public void getCollections(String url, String reqJson, Object tag, ApiCallbackListener<GetCollectionsResp> listener);
    public void searchPerson(String url, String reqJson, Object tag, ApiCallbackListener<SearchPersonResp> listener);
    public void getClazzDetail(String url, String reqJson, Object tag, ApiCallbackListener<GetClazzDetailResp> listener);
    public void postHomework(String url, String reqJson, Object tag, ApiCallbackListener<PostHomeworkResp> listener);
    public void getTeacherHomework(String url, String reqJson, Object tag, ApiCallbackListener<GetTeacherHomeworkResp> listener);
    public void getHomeworkById(String url, String reqJson, Object tag, ApiCallbackListener<GetHomeworkByIdResp> listener);
    public void submitStudent(String url, String reqJson, Object tag, ApiCallbackListener<SubmitStudentResp> listener);
    public void getStudentHomework(String url, String reqJson, Object tag, ApiCallbackListener<GetStudentHomeworkResp> listener);
    public void getStudentHomeworkById(String url, String reqJson, Object tag, ApiCallbackListener<GetStudentHomeworkByIdResp> listener);
    public void submitStudentHomework(String url, String reqJson, Object tag, ApiCallbackListener<SubmitStudentHomeworkResp> listener);
    public void getStudentAnalysis(String url, String reqJson, Object tag, ApiCallbackListener<GetStudentAnalysisResp> listener);
    public void getHead(String url, String reqJson, Object tag, ApiCallbackListener<GetHeadResp> listener);
}
