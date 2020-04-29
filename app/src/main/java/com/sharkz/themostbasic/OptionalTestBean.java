package com.sharkz.themostbasic;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/4/23  15:43
 * 描    述
 * 修订历史：
 * ================================================
 */
public class OptionalTestBean {

    private OptionalTestBean2 bean2;

    public OptionalTestBean() {
    }

    public OptionalTestBean(OptionalTestBean2 bean2) {
        this.bean2 = bean2;
    }

    public OptionalTestBean2 getBean2() {
        return bean2;
    }

    public void setBean2(OptionalTestBean2 bean2) {
        this.bean2 = bean2;
    }

    public static class OptionalTestBean2{
        private String name;
        private String color;

        public OptionalTestBean2() {

        }

        public OptionalTestBean2(String name, String color) {
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

}
