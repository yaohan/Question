package com.ssdut411.app.questionanswer.core;

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

/**
 * 接收App层的各种Action
 *
 * Created by yao_han on 2015/11/24.
 */
public interface AppAction {
    public void login(LoginReq req, ActionCallbackListener<LoginResp> listener);
    public void forgetPassword(ForgetPasswordReq req, ActionCallbackListener<ForgetPasswordResp> listener);

    public void register(RegisterReq req, ActionCallbackListener<RegisterResp> listener);

    public void finishInfo(FinishInfoReq req, ActionCallbackListener<FinishInfoResp> listener);

    public void getInfo(GetInfoReq req, ActionCallbackListener<GetInfoResp> listener);

    public void getSchoolList(GetSchoolReq req, ActionCallbackListener<GetSchoolResp> listener);

    public void addSchool(AddSchoolReq req, ActionCallbackListener<AddSchoolResp> listener);

    public void getClazzList(GetClazzReq req, ActionCallbackListener<GetClazzResp> listener);

    public void addClazz(AddClazzReq req, ActionCallbackListener<AddClazzResp> listener);

    public void getTestSites(GetTestSitesReq req, ActionCallbackListener<GetTestSitesResp> listener);

    public void getNewTest(GetNewTestReq req, ActionCallbackListener<GetNewTestResp> listener);

    public void getMyTest(GetMyTestReq req, ActionCallbackListener<GetMyTestResp> listener);

    public void getSelfTestById(GetSelfTestByIdReq req,ActionCallbackListener<GetSelfTestByIdResp> listener);

    public void submitTest(SubmitTestReq req, ActionCallbackListener<SubmitTestResp> listener);

    public void collection(CollectionReq req, ActionCallbackListener<CollectionResp> listener);

    public void unCollection(UnCollectionReq req, ActionCallbackListener<UnCollectionResp> listener);

    public void getErrors(GetErrorsReq req, ActionCallbackListener<GetErrorsResp> listener);

    public void getCollections(GetCollectionsReq req, ActionCallbackListener<GetCollectionsResp> listener);

    public void searchPerson(SearchPersonReq req, ActionCallbackListener<SearchPersonResp> listener);

    public void getClazzDetail(GetClazzDetailReq req, ActionCallbackListener<GetClazzDetailResp> listener);

    public void postHomework(PostHomeworkReq req, ActionCallbackListener<PostHomeworkResp> listener);

    public void getTeacherHomework(GetTeacherHomeworkReq req, ActionCallbackListener<GetTeacherHomeworkResp> listener);

    public void getHomeworkById(GetHomeworkByIdReq req, ActionCallbackListener<GetHomeworkByIdResp> listener);

    public void submitStudent(SubmitStudentReq req, ActionCallbackListener<SubmitStudentResp> listener);

    public void getStudentHomework(GetStudentHomeworkReq req, ActionCallbackListener<GetStudentHomeworkResp> listener);

    public void getStudentHomeworkById(GetStudentHomeworkByIdReq req, ActionCallbackListener<GetStudentHomeworkByIdResp> listener);

    public void submitStudentHomework(SubmitStudentHomeworkReq req, ActionCallbackListener<SubmitStudentHomeworkResp> listener);

    public void getStudentAnalysis(GetStudentAnalysisReq req, ActionCallbackListener<GetStudentAnalysisResp> listener);

    public void getHead(GetHeadReq getHeadReq, ActionCallbackListener<GetHeadResp> listener);
}
