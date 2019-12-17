package domain;

import java.io.IOException;

import java.util.concurrent.RecursiveTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Searching extends RecursiveTask<String> {
    private static String text;
    private static volatile int deep;
    private static volatile String result;

    private Searching searching;
    private String url;

    public Searching(String url) {
        this.url = url;
    }

    public Searching(String url, String text, int deep) {
        this.url = url;
        this.text = text;
        this.deep = deep;
    }

    @Override
    protected String compute() {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IllegalArgumentException | IOException e) {
            return "Problem with connect";
        }
        String body = doc.body().text();

        if (body.contains(text)) {
            if (result == null) {
                result = url.toString();
            } else if (!result.contains(url)) {
                result += "\n" + url;
            }
        }

        if (deep > 1) {
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                searching = new Searching(link.attr("abs:href"));
                searching.fork();
            }
            deep--;
        }
        if (searching != null) {
            String join = searching.join();
            if (join != null) {
                result += join;
            }
        }
        return result;
    }
}
