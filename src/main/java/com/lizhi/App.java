package com.lizhi;

import com.lizhi.common.Constants;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author: 荔枝
 * @date: 2022/7/25 22 23
 * @description:
 */
public class App {
    static {
        System.setProperty("webdriver.chrome.driver", "C:\\chromeDriver\\chromedriver.exe");
    }
    public static ChromeDriver chromeDriver = new ChromeDriver();


    public static void login() throws InterruptedException {
        //登陆页面
        chromeDriver.get(Constants.LOGIN_LINK);
        chromeDriver.manage().window().maximize();
        JavascriptExecutor javaScriptExecutor = chromeDriver;
        //上下移动
        javaScriptExecutor.executeScript("var q=document.documentElement.scrollTop=1000");
        //开始页面
        chromeDriver.manage().timeouts().implicitlyWait(10000, TimeUnit.MICROSECONDS);
        Thread.sleep(10000);
        System.out.println("登陆完毕");
    }

    public static int getScore(String arg) throws InterruptedException {
        openUrl(Constants.SCORES_LINK);
        Set<String> windowHandles = chromeDriver.getWindowHandles();
        List<String> handles = new ArrayList<>(windowHandles);
        if (handles.size() == 3) {
            chromeDriver.switchTo().window(handles.get(2));
        } else {
            chromeDriver.switchTo().window(handles.get(1));
        }
        waitByElement("my-points-card-text");
        List<WebElement> elements = chromeDriver.findElements(By.className("my-points-card-text"));
        int articleScore = 0;
        int videoScore = 0;
        int videoDurationScore = 0;
        int dailyScore = 0;
        int specialScore = 0;
        int weeklyScore = 0;
        for (int i = 0; i < elements.size(); i++) {
            if (i == 1) {
                articleScore = Integer.parseInt(elements.get(i).getAttribute("innerText").split("分")[0]);
            }
            if (i == 2) {
                videoScore = Integer.parseInt(elements.get(i).getAttribute("innerText").split("分")[0]);
            }
            if (i == 3) {
                videoDurationScore = Integer.parseInt(elements.get(i).getAttribute("innerText").split("分")[0]);
            }
            if (i == 4) {
                dailyScore = Integer.parseInt(elements.get(i).getAttribute("innerText").split("分")[0]);
            }
            if (i == 5) {
                specialScore = Integer.parseInt(elements.get(i).getAttribute("innerText").split("分")[0]);
            }
            if (i == 6) {
                weeklyScore = Integer.parseInt(elements.get(i).getAttribute("innerText").split("分")[0]);
            }
        }
        chromeDriver.close();
        chromeDriver.switchTo().window(handles.get(0));
        if ("文章".equals(arg)) {
            return articleScore;
        }
        if ("视频".equals(arg)) {
            return Math.min(videoScore, videoDurationScore);
        }
        if ("每日答题".equals(arg)) {
            return dailyScore;
        }
        if ("专项答题".equals(arg)) {
            return specialScore;
        }
        if ("每周答题".equals(arg)) {
            return weeklyScore;
        }
        return 0;
    }

    public static void readArticle() throws InterruptedException {
        System.out.println("开始文章阅读");
        openUrl(Constants.ARTICLES_LINK);
        // chromeDriver.get(Constants.ARTICLES_LINK);
        List<String> handles = getHandles();
        chromeDriver.switchTo().window(handles.get(1));
        //等待10秒
        int articleScore = 0;
        waitByElement("_3wnLIRcEni99IWb4rSpguK");
        List<WebElement> elements = chromeDriver.findElements(By.className("_3wnLIRcEni99IWb4rSpguK"));
        for (WebElement webElement : elements) {
            articleScore = getScore("文章");
            chromeDriver.switchTo().window(handles.get(1));
            if (12 <= articleScore) {
                System.out.println("跳出去的分数" + articleScore);
                chromeDriver.switchTo().window(handles.get(1));
                chromeDriver.close();
                List<String> handles1 = getHandles();
                chromeDriver.switchTo().window(handles.get(0));
                break;
            }
            webElement.click();
            Thread.sleep(5000);
            //重新获取句柄
            List<String> handles2 = getHandles();
            //跳到第3页
            chromeDriver.switchTo().window(handles2.get(2));
            JavascriptExecutor javaScriptExecutor = chromeDriver;
            //上下移动
            for (int i = 1; i <= 5; i++) {
                javaScriptExecutor.executeScript("var q=document.documentElement.scrollTop=" + 200 * i);
                Thread.sleep(2000);
            }
            javaScriptExecutor.executeScript("var q=document.documentElement.scrollTop=1000");
            //等待10S
            Thread.sleep(10000);
            chromeDriver.close();
            chromeDriver.switchTo().window(handles2.get(1));
        }
        if (articleScore < 12) {
            chromeDriver.switchTo().window(handles.get(1));
            chromeDriver.close();
            List<String> handles1 = getHandles();
            chromeDriver.switchTo().window(handles.get(0));
        }
        System.out.println("阅读文章结束");
    }

    public static void writeVideosUrl() throws InterruptedException, IOException {

        String path = "E:\\ideaWorkSpace\\xuexiqingguo\\src\\main\\java\\com\\lizhi\\videoFile\\videos.txt";
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        bufferedWriter.write("0");
        bufferedWriter.newLine();
        chromeDriver.get(Constants.VIDEO_LINK);
        Thread.sleep(10000);
        chromeDriver.findElement(By.className("img")).click();
        for (int i = 1; i <= 126; i++) {
            if (i == 1) {
                chromeDriver.findElements(By.className("btn")).get(5).click();
                Thread.sleep(5000);
            } else {
                chromeDriver.findElements(By.className("btn")).get(1).click();
            }
            List<WebElement> elements = chromeDriver.findElements(By.className("_1PcbELBKVoVrF5XKNSE_SF"));
            for (WebElement element : elements) {
                String url = element.getAttribute("data-link-target");
                bufferedWriter.write(url);
                bufferedWriter.newLine();
            }
        }
    }

    public static void watchVideos() throws IOException, InterruptedException {
        System.out.println("开始视频学习");
        String path = "E:\\ideaWorkSpace\\xuexiqingguo\\src\\main\\java\\com\\lizhi\\videoFile\\videos.txt";
        List<String> urls;
        double random = Math.random();
        try (FileReader fr = new FileReader(path);
             BufferedReader br = new BufferedReader(fr)) {
            long round = Math.round(random * 2300);
            urls = br.lines().skip(round).limit(round + 93).collect(Collectors.toList());
        }
        if (!urls.isEmpty()) {
            for (String url : urls) {
                int score = getScore("视频");
                if (score >= 6) {
                    System.out.println("跳出去视频分数" + score);
                    break;
                }
                openUrl(url);
                Thread.sleep(5000);
                Set<String> windowHandles = chromeDriver.getWindowHandles();
                List<String> handles = new ArrayList<>(windowHandles);
                chromeDriver.switchTo().window(handles.get(1));
                chromeDriver.executeScript("var q=document.documentElement.scrollTop=500");
                WebElement video = chromeDriver.findElement(By.cssSelector("video"));
                chromeDriver.executeScript("arguments[0].muted = true;",video);
                try {
                    chromeDriver.findElement(By.className("prism-big-play-btn")).click();
                } catch (Exception e) {
                    continue;
                }
                Thread.sleep(60000 + Math.round(random * 10000));
                chromeDriver.close();
                chromeDriver.switchTo().window(handles.get(0));
            }
        }
        System.out.println("视频学习结束");
    }

    public static void dailyAnswerQuestions() throws InterruptedException {
        System.out.println("开始每日答题");
        int score = getScore("每日答题");
        //每日答题，计分只记第一次，第二次答题不计分
        if (score > 0) {
            System.out.println("每日答题已做" + score);
            return;
        }
        openUrl(Constants.DAILY_EXAM_PRACTICE);
        List<String> handles = getHandles();
        chromeDriver.switchTo().window(handles.get(1));
        waitByElement("q-header");
        Thread.sleep(5000);
        try {
            getPagerAndCompleteQuestion(handles);
        } catch (Exception e) {
            e.printStackTrace();
            chromeDriver.close();
            chromeDriver.switchTo().window(handles.get(0));
            System.out.println("每日答题请手动查看");
        }
        score = getScore("每日答题");
        System.out.println("今日答题分数：" + score);
    }

    public static void specialAnswerQuestions() throws InterruptedException {
        System.out.println("开始专项答题");
        int score = getScore("专项答题");
        // 专项答题，计分只记第一次，第二次答题不计分
        if (score > 0) {
            System.out.println("专项答题已做");
            return;
        }
        openUrl(Constants.SPECIAL_QUESTIONS);
        List<String> handles = getHandles();
        chromeDriver.switchTo().window(handles.get(1));
        waitByElement("item");
        WebElement right = null;
        Thread.sleep(3000);
        List<WebElement> items = chromeDriver.findElements(By.className("item"));
        if (items.isEmpty()) {
            System.out.println("没有专项答题题目");
            chromeDriver.close();
            chromeDriver.switchTo().window(handles.get(0));
            return;
        }
        for (WebElement element : items) {
            WebElement element1 = element.findElement(By.className("right"));
            String text = element1.getText().trim();
            if ("开始答题".equals(text)) {
                WebElement left = element.findElement(By.className("left"));
                String text1 = left.getText().trim().replace("\n", "");
                System.out.println("做的题目是：" + text1);
                right = element1;
                break;
            }
        }
        if (right == null) {
            System.out.println("没有专项答题题目");
            return;
        }
        right.click();
        waitByElement("question-grid");
        WebElement questionsNumberElement = chromeDriver.findElement(By.className("question-grid"));
        List<WebElement> block_disabled = questionsNumberElement.findElements(By.cssSelector(".block.disabled"));
        int questionsNumber = block_disabled.size() + 1;
        try {
            for (int i = 0; i < questionsNumber; i++) {
                completeQuestions();
            }
        } catch (Exception e) {
            e.printStackTrace();
            chromeDriver.close();
            chromeDriver.switchTo().window(handles.get(0));
            System.out.println("专项答题请手动查看");
        }
        Thread.sleep(5000);
        chromeDriver.close();
        chromeDriver.switchTo().window(handles.get(0));
        score = getScore("专项答题");
        System.out.println("专项答题答题分数：" + score);
    }

    public static void weeklyQuiz() throws InterruptedException {
        System.out.println("开始每周答题");
        int score = getScore("每周答题");
        // 专项答题，计分只记第一次，第二次答题不计分
        if (score > 0) {
            System.out.println("每周答题已做");
            return;
        }
        openUrl(Constants.WEEKLY_QUESTIONS);
        List<String> handles = getHandles();
        chromeDriver.switchTo().window(handles.get(1));
        waitByElement("week");
        List<WebElement> weekElements = chromeDriver.findElements(By.className("week"));
        if (weekElements.isEmpty()) {
            System.out.println("每周答题没有题目");
            chromeDriver.close();
            chromeDriver.switchTo().window(handles.get(0));
            return;
        }
        WebElement right = null;
        for (WebElement weekElement : weekElements) {
            WebElement left = weekElement.findElement(By.className("left"));
            WebElement element = weekElement.findElement(By.cssSelector(".ant-btn.button.ant-btn-primary"));
            String buttonText = element.getText();
            if ("开始答题".equals(buttonText)) {
                right = element;
                break;
            }
        }
        if (right == null) {
            System.out.println("每周答题没有题目");
            return;
        }
        right.click();
        waitByElement("pager");
        try {
            Thread.sleep(5000);
            getPagerAndCompleteQuestion(handles);
        } catch (Exception e) {
            e.printStackTrace();
            chromeDriver.close();
            chromeDriver.switchTo().window(handles.get(0));
            System.out.println("每周答题请手动查看");
        }

        score = getScore("每周答题");
        System.out.println("每周答题答题分数：" + score);

    }

    private static void getPagerAndCompleteQuestion(List<String> handles) throws InterruptedException {
        WebElement pager = chromeDriver.findElement(By.className("pager"));
        String text = pager.getText();
        String[] split = text.split("\\/");
        int size = Integer.parseInt(split[1]);
        for (int i = 0; i < size; i++) {
            completeQuestions();
        }
        Thread.sleep(5000);
        chromeDriver.close();
        chromeDriver.switchTo().window(handles.get(0));
    }

    public static void completeQuestions() throws InterruptedException {
        Thread.sleep(2000);
        String questionType = chromeDriver.findElement(By.className("q-header")).getAttribute("innerText");
        if (questionType.contains("（")) {
            questionType = questionType.split("（")[0];
        }
        WebElement questionBodyElement = chromeDriver.findElement(By.className("q-body"));
        //查看提示
        Thread.sleep(5000);
        List<WebElement> tipsElement = chromeDriver.findElements(By.cssSelector(".tips"));
        if (tipsElement.isEmpty()) {
            if ("填空题".equals(questionType)) {
                fillInBlank(new ArrayList<>());
            }
            if ("单选题".equals(questionType) || "多选题".equals(questionType)) {
                multipleChoice(new ArrayList<>());
            }
        }
        tipsElement.get(0).click();
        Thread.sleep(3000);
        List<WebElement> elements = chromeDriver.findElements(By.cssSelector(".ant-popover.line-feed"));
        if (elements.isEmpty()) {
            elements = chromeDriver.findElements(By.className("line-feed"));
        }
        String tip = elements.get(0).getAttribute("innerHTML");
        String reg = "(?<=>).*?(?=<)";
        Pattern compile = Pattern.compile(reg);
        Matcher matcher = compile.matcher(tip);
        List<String> answers = new ArrayList<>();
        while (matcher.find()) {
            answers.add(matcher.group());
        }
        //点击空白处
        questionBodyElement.click();
        if ("填空题".equals(questionType)) {
            fillInBlank(answers);
        }
        if ("单选题".equals(questionType) || "多选题".equals(questionType)) {
            multipleChoice(answers);
        }
    }

    public static void multipleChoice(List<String> answers) throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> elements = chromeDriver.findElements(By.cssSelector(".q-answer.choosable"));
        if (elements.isEmpty()) {
            elements = chromeDriver.findElements(By.className("q-answer"));
        }
        boolean flag = true;
        if (answers.isEmpty()) {
            elements.get(0).click();
            WebElement element = chromeDriver.findElement(By.cssSelector(".ant-btn.next-btn.ant-btn-primary"));
            String text = element.getText();
            if ("下一题".equals(text)) {
                element.click();
            }
            flag = false;
        } else {
            for (WebElement element : elements) {
                String innerText = element.getAttribute("innerText");
                String option = innerText.split("\\.")[1].trim();
                if (answers.contains(option)) {
                    flag = false;
                    Thread.sleep(500);
                    element.click();
                }
            }
            try {
                WebElement element1 = chromeDriver.findElement(By.cssSelector(".ant-btn.submit-btn.ant-btn-primary.ant-btn-background-ghost"));
                //这个是交卷
                element1.click();
            } catch (Exception e) {

            }
        }
        if (flag) {
            elements.get(0).click();
        }
        WebElement element = chromeDriver.findElement(By.cssSelector(".ant-btn.next-btn.ant-btn-primary"));
        element.click();
        Thread.sleep(2000);
        List<WebElement> explain = chromeDriver.findElements(By.className("explain"));
        if (!explain.isEmpty()) {
            element = chromeDriver.findElement(By.cssSelector(".ant-btn.next-btn.ant-btn-primary"));
            element.click();
        }
    }

    public static void fillInBlank(List<String> answers) throws InterruptedException {
        waitByElement("blank");
        List<WebElement> blank = chromeDriver.findElements(By.className("blank"));
        boolean flag = true;
        if (!answers.isEmpty()) {
            for (int j = 0; j < blank.size(); j++) {
                blank.get(j).sendKeys(answers.get(j));
                Thread.sleep(1000);
                flag = false;
            }
        } else {
            for (WebElement webElement : blank) {
                webElement.click();
                Thread.sleep(200);
                webElement.sendKeys("富强");
                WebElement element = chromeDriver.findElement(By.cssSelector(".ant-btn.next-btn.ant-btn-primary"));
                String text = element.getText();
                if ("下一题".equals(text)) {
                    element.click();
                }
                flag = false;
            }
        }
        if (flag) {
            blank.get(0).sendKeys("富强");
        }
        WebElement element = chromeDriver.findElement(By.cssSelector(".ant-btn.next-btn.ant-btn-primary"));
        element.click();
        Thread.sleep(2000);
        List<WebElement> explain = chromeDriver.findElements(By.className("explain"));
        if (!explain.isEmpty()) {
            element = chromeDriver.findElement(By.cssSelector(".ant-btn.next-btn.ant-btn-primary"));
            element.click();

        }
    }

    public static void waitByElement(String arg) throws InterruptedException {
        Thread.sleep(5000);
        // WebDriverWait webDriverWait = new WebDriverWait(chromeDriver, 1000);
        // webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.className(arg)));
    }

    public static List<String> getHandles() {
        return new ArrayList<>(chromeDriver.getWindowHandles());
    }

    public static void openUrl(String url) {
        String cmd = "window.open('" + url + "');";
        chromeDriver.executeScript(cmd);
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        long start = System.currentTimeMillis();
        //人为模拟电脑操作
        login();
        readArticle();
        watchVideos();
        dailyAnswerQuestions();
        specialAnswerQuestions();
        weeklyQuiz();
        //模拟手机操作
        long end = System.currentTimeMillis();
        System.out.println(DurationFormatUtils.formatDuration(end - start, "HH:mm:ss"));
    }


}
