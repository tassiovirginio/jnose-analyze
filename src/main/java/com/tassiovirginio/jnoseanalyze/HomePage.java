package com.tassiovirginio.jnoseanalyze;

import com.tassiovirginio.jnoseanalyze.entidades.TestSmell;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.util.lang.Bytes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@WicketHomePage
public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    private WebMarkupContainer container;

    private ListView<TestSmell> listview;

    private AjaxButton btSubmit;

    public HomePage() {
        this(new ArrayList<>());
    }

    public HomePage(List<TestSmell> listaTestSmells) {

        WebRequest req = (WebRequest) RequestCycle.get().getRequest();
        HttpServletRequest httpReq = (HttpServletRequest) req.getContainerRequest();
        String clientAddress = httpReq.getRemoteHost();

        WebMarkupContainer containerFeedback = new WebMarkupContainer("containerFeedback");
        containerFeedback.setOutputMarkupId(true);
        containerFeedback.add(new AjaxSelfUpdatingTimerBehavior(Duration.ofSeconds(10)));
        add(containerFeedback);

        FeedbackPanel feedbackPanel1 = new FeedbackPanel("feedback");
        feedbackPanel1.setOutputMarkupId(true);
        containerFeedback.add(feedbackPanel1);

        Form form = new Form<Void>("fom");
        form.setMultiPart(true);
        form.setMaxSize(Bytes.kilobytes(1000));
        form.setOutputMarkupId(true);

        FileUploadField fileUpload1 = new FileUploadField("fileUpload1");
        fileUpload1.setRequired(true);
        form.add(fileUpload1);

        FileUploadField fileUpload2 = new FileUploadField("fileUpload2");
        fileUpload2.setRequired(false);
        form.add(fileUpload2);


        btSubmit = new AjaxButton("btSubmit") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {

                final FileUpload uploadedFile1 = fileUpload1.getFileUpload();

                final FileUpload uploadedFile2 = fileUpload2.getFileUpload();

                File classTestFile = null;
                File classProductionFile = null;

                if(uploadedFile1 != null){
                    try {
                        classTestFile = uploadedFile1.writeToTempFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if(uploadedFile2 != null){
                    try {
                        classProductionFile = uploadedFile1.writeToTempFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

//                File newFile = null;

//                if (uploadedFile1 != null) {
//                    newFile = new File("src/main/webapp/fotos/" + uploadedFile1.getClientFileName());
//                    if (newFile.exists()) {
//                        newFile.delete();
//                    }
//                    try {
//                        newFile.createNewFile();
//                        uploadedFile1.writeTo(newFile);
//                        System.out.println("saved file: " + uploadedFile1.getClientFileName());
//                        info("saved file: " + uploadedFile1.getClientFileName());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        throw new IllegalStateException("Error");
//                    }
//                }


                List<TestSmell> listaTestSmell = new ArrayList<>(); //DBCore.getPatrimonios(usuario);
                listview.setList(listaTestSmell);

                target.add(container, form, containerFeedback);

                info("ClassTest: " + uploadedFile1.getClientFileName());

                if(classProductionFile != null)
                info("ClassProduction: " + uploadedFile2.getClientFileName());
            }
        };
        form.add(btSubmit);

        add(form);

        WebMarkupContainer containerInfo = new WebMarkupContainer("containerInfo");
        containerInfo.setOutputMarkupId(true);
        containerInfo.add(new AjaxSelfUpdatingTimerBehavior(Duration.ofSeconds(1)));
        add(containerInfo);

        listview = new ListView<>("listview", listaTestSmells) {
            protected void populateItem(ListItem<TestSmell> item) {


            }
        };

        container = new WebMarkupContainer("container");
        container.setOutputMarkupId(true);
        container.add(listview);
        add(container);

    }

}

