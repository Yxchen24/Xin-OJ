package com.xin.xinoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xin.xinoj.common.ErrorCode;
import com.xin.xinoj.exception.BusinessException;
import com.xin.xinoj.mapper.QuestionSubmitMapper;
import com.xin.xinoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xin.xinoj.model.entity.Question;
import com.xin.xinoj.model.entity.QuestionSubmit;
import com.xin.xinoj.model.entity.QuestionSubmit;
import com.xin.xinoj.model.entity.User;
import com.xin.xinoj.model.enums.JudgeInfoMessageEnum;
import com.xin.xinoj.model.enums.QuestionSubmitLanguageEnum;
import com.xin.xinoj.model.enums.QuestionSubmitStatusEnum;
import com.xin.xinoj.service.QuestionService;
import com.xin.xinoj.service.QuestionSubmitService;
import com.xin.xinoj.service.QuestionSubmitService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author 34303
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2025-07-17 19:10:57
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {
    @Resource
    private QuestionService questionService;

    /**
     * 问题提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {

        //校验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        Long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        // 设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WATING.getValue());
        questionSubmit.setJudgeInfo(JudgeInfoMessageEnum.WAITING.getValue());
        boolean result = this.save(questionSubmit);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "提交问题失败");
        }
        return questionSubmit.getId();
    }



}




