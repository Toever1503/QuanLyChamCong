package com.service.impl;

import com.dto.SalaryDto;
import com.service.ISalaryService;
import com.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImp implements IMailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Autowired
    ISalaryService salaryService;

    @Value("${mail.smtp.username}")
    String mailFrom;

    @Override
    public void sendHtmlMail(String toAddress, String subject, Object model, String filePath, String content, SalaryDto salaryDto) throws MessagingException {
            final Context context =new Context(LocaleContextHolder.getLocale());
            context.setVariable("salary", salaryDto);
            final MimeMessage message =this.javaMailSender.createMimeMessage();
            final MimeMessageHelper helper =new MimeMessageHelper(message, "UTF-8");
            helper.setTo(toAddress);
            helper.setFrom(mailFrom);
            helper.setSubject(subject);
            String templateHtml =templateEngine.process("MailTemplate",context);
            helper.setText(templateHtml,true);
            this.javaMailSender.send(message);
    }
}
