package com.tassiovirginio.jnoseanalyze;

import br.ufba.jnose.core.Config;
import br.ufba.jnose.core.JNoseCore;
import br.ufba.jnose.dto.TestClass;
import br.ufba.jnose.dto.TestSmell;
import com.tassiovirginio.jnoseanalyze.entidades.TestSmellBean;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
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

    public HomePage(List<TestSmell> listaTestSmellBeans) {

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
                        classTestFile = new File(uploadedFile1.getClientFileName());
                        uploadedFile1.writeTo(classTestFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if(uploadedFile2 != null){
                    try {
                        classProductionFile = new File(uploadedFile2.getClientFileName());
                        uploadedFile2.writeTo(classProductionFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                TestClass testClass = new TestClass();
                testClass.setName(uploadedFile1.getClientFileName());
                testClass.setPathFile(classTestFile.getAbsolutePath());
                if(classProductionFile != null)
                testClass.setProductionFile(classProductionFile.getAbsolutePath());
                testClass.setProjectName("");

                //Mudar a lógica depois no Core
                testClass.setJunitVersion(TestClass.JunitVersion.JUnit4);


                JNoseCore jNoseCore = new JNoseCore(loadConfig(uploadedFile2 != null));
                Boolean isClassTest = jNoseCore.isTestFile(testClass);
                jNoseCore.getTestSmells(testClass);

                List<TestSmell> listaTestSmellBean = testClass.getListTestSmell();
                listview.setList(listaTestSmellBean);

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

        listview = new ListView<TestSmell>("listview", listaTestSmellBeans) {
            protected void populateItem(ListItem<TestSmell> item) {
                TestSmell testSmell = item.getModelObject();

                item.add(new Label("nome",testSmell.getName()));
                item.add(new Label("method",testSmell.getMethod()));
                item.add(new Label("range",testSmell.getRange()));

            }
        };

        container = new WebMarkupContainer("container");
        container.setOutputMarkupId(true);
        container.add(listview);
        add(container);

    }

    private Config loadConfig(boolean classProduction){
        Config config = new Config() {
            @Override
            public Boolean assertionRoulette() {
                return true;
            }

            @Override
            public Boolean conditionalTestLogic() {
                return true;
            }

            @Override
            public Boolean constructorInitialization() {
                return true;
            }

            @Override
            public Boolean defaultTest() {
                return true;
            }

            @Override
            public Boolean dependentTest() {
                return true;
            }

            @Override
            public Boolean duplicateAssert() {
                return true;
            }

            @Override
            public Boolean eagerTest() {
                return true;
            }

            @Override
            public Boolean emptyTest() {
                return true;
            }

            @Override
            public Boolean exceptionCatchingThrowing() {
                return true;
            }

            @Override
            public Boolean generalFixture() {
                return true;
            }

            @Override
            public Boolean mysteryGuest() {
                return true;
            }

            @Override
            public Boolean printStatement() {
                return true;
            }

            @Override
            public Boolean redundantAssertion() {
                return true;
            }

            @Override
            public Boolean sensitiveEquality() {
                return true;
            }

            @Override
            public Boolean verboseTest() {
                return true;
            }

            @Override
            public Boolean sleepyTest() {
                return true;
            }

            @Override
            public Boolean lazyTest() {
                return true;
            }

            @Override
            public Boolean unknownTest() {
                return true;
            }

            @Override
            public Boolean ignoredTest() {
                return true;
            }

            @Override
            public Boolean resourceOptimism() {
                return true;
            }

            @Override
            public Boolean magicNumberTest() {
                return true;
            }
        };

        return config;
    }

}

