package com.xin.xinoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xin.xinoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xin.xinoj.model.entity.QuestionSubmit;
import com.xin.xinoj.model.entity.User;

/**
* @author 34303
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2025-07-17 19:10:57
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 问题提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return 题目提交id
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);


}
