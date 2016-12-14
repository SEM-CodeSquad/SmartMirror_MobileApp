/*
 * Copyright 2016 CodeHigh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2016 CodeHigh.
 *     Permission is granted to copy, distribute and/or modify this document
 *     under the terms of the GNU Free Documentation License, Version 1.3
 *     or any later version published by the Free Software Foundation;
 *     with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *     A copy of the license is included in the section entitled "GNU
 *     Free Documentation License".
 */

package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExtraInfoHandler;


import android.os.AsyncTask;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import postApp.Presenters.MenuPresenters.FragmentPresenters.ExtraInfoPresenter.ContactPresenter;

/**
 * Class that is responsible for the logic of the ContactView and interacts with the presenter
 */

public class ContactHandler {

    private Session session = null;
    private ContactPresenter ContactPresenter;
    private String rec;
    private String subject;
    private String textMessage;

    /**
     * Constructor that sets the contactpresenter
     * @param ContactPresenter the presenter
     */
    public ContactHandler(ContactPresenter ContactPresenter) {
        this.ContactPresenter = ContactPresenter;
    }

    /**
     * Method for sending a email, checks if email is valid, if subject is bigger than 1 char, and the message is bigger than 1 char
     * @param receipent The receipent string
     * @param Subject The subject String
     * @param Message The message String
     */
    public void SendEmail(String receipent, String Subject, String Message) {
        if (isEmailValid(receipent)) {
            if (Subject.length() > 0) {
                if (Message.length() > 0) {

                    ContactPresenter.Loading();
                    rec = receipent;
                    subject = Subject;
                    textMessage = Message;

                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.socketFactory.port", "465");
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.port", "465");

                    session = Session.getDefaultInstance(props, new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("codehighsmartmirror@gmail.com", "codehigh123");
                        }
                    });


                    RetreiveFeedTask task = new RetreiveFeedTask();
                    task.execute();
                } else {
                    ContactPresenter.NotCorrect("There is no text");
                }
            } else {
                ContactPresenter.NotCorrect("There is no subject");
            }
        } else {
            ContactPresenter.NotCorrect("Receipent must be a valid Email");
        }
    }

    /**
     * Function that checks if its a email
     *
     * @param email the Charsequence that needs to be checked
     * @return true or false
     */
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Async class that tries to send a email.
     */
    private class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("codehighsmartmirror@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("codehighsmartmirror@gmail.com"));
                message.setSubject("Email from: " + rec + " Subject is: " + subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * When done it calls the presenters method for SentEmail
         * @param result the resultstring
         */
        @Override
        protected void onPostExecute(String result) {
            ContactPresenter.SentEmail();
        }
    }
}
