package com.sharkz.themostbasic.test1;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/20  12:40
 * 描    述
 * 修订历史：
 * ================================================
 */
public class ShareItem {


    private static final int TYPE_LINK = 0;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_TEXT = 2;
    private static final int TYPE_IMAGE_TEXT = 3;


    private int type;
    private String title;
    private String content;
    private String imagePath;
    private String link;

    public ShareItem() {

    }

    public ShareItem(int type, String title, String content, String imagePath, String link) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.link = link;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public interface ShareListener {

        int STATE_SUCC = 0;
        int STATE_FAIL = 1;

        void onCallback(int state, String msg);
    }

}
