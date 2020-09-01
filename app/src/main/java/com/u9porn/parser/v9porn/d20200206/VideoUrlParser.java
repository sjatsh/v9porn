package com.u9porn.parser.v9porn.d20200206;

import com.orhanobut.logger.Logger;
import com.u9porn.data.db.entity.VideoResult;
import com.u9porn.data.model.User;
import com.u9porn.parser.v9porn.BaseVideoPlayUrlParser;
import com.u9porn.parser.v9porn.VideoPlayUrlParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.inject.Inject;

public class VideoUrlParser extends BaseVideoPlayUrlParser implements VideoPlayUrlParser {

    @Inject
    public VideoUrlParser() {
    }

    @Override
    public VideoResult parseVideoPlayUrl(String html, User user) {
        Logger.t(TAG).d("原网页内容", html);

        VideoResult videoResult = new VideoResult();
        Document document = Jsoup.parse(html);
        Element element = document.getElementById("player_one");

        String imgUrl = element.attr("poster");
        String videoId = imgUrl.substring(imgUrl.indexOf("thumb") + 6, imgUrl.lastIndexOf("."));
        videoResult.setVideoId(videoId);
        Logger.t(TAG).d("视频Id：" + videoId);

        Element jsElement = element.select("script").first();
        String jsTagString = jsElement.toString();
        String jsScriptVideoUrl = jsTagString.substring(jsTagString.indexOf("strencode"), jsTagString.indexOf(");"));

        videoResult.setVideoUrl(jsScriptVideoUrl);
//        String videoUrl = element.selectFirst("source").attr("src");
//        videoResult.setVideoUrl(videoUrl);
//        int startIndex = videoUrl.lastIndexOf("/");
//        int endIndex = videoUrl.indexOf(".mp4");
//        String videoId = videoUrl.substring(startIndex + 1, endIndex);
//        videoResult.setVideoId(videoId);
//        Logger.t(TAG).d("视频Id：" + videoId);
        parserOtherInfo(document, videoResult, user);
        return videoResult;
    }
}
