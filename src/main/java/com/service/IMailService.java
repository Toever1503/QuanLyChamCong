package com.service;

import com.dto.SalaryDto;

import javax.mail.MessagingException;


public interface IMailService {
    public void sendHtmlMail(String toAddress, String subject, Object model, String filePath, String content, SalaryDto salaryDto) throws MessagingException;

}
