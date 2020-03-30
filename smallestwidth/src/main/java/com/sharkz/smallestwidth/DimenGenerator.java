package com.sharkz.smallestwidth;


import java.io.File;

/**
 *
 * 这个是Android 屏幕适配 工具类
 * 实现原理：Android会识别屏幕可用高度和宽度的最小尺寸的dp值（其实就是手机的宽度值），然后根据识别到的结果去资源文件中寻找对应限定符的文件夹下的资源文件。
 *   sw限定符适配 和 宽高限定符适配类似，区别在于，前者有很好的容错机制，如果没有value-sw360dp文件夹，系统会向下寻找，比如离360dp最近的只有value-sw350dp，那么Android就会选择value-sw350dp文件夹下面的资源文件。这个特性就完美的解决了上文提到的宽高限定符的容错问题。
 *   优点:1.非常稳定，极低概率出现意外
 *     2.不会有任何性能的损耗
 *     3.适配范围可自由控制，不会影响其他三方库
 *   缺点：就是多个dimens文件可能导致apk变大，几百k。
 *
 * 作者：lzy2626
 * 链接：https://www.jianshu.com/p/337c47721690
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 * DimenGenerator文件中填写设计稿的尺寸(DESIGN_WIDTH是设计稿宽度，DESIGN_HEIGHT是设计稿高度)
 * TODO https://github.com/ladingwu/dimens_sw
 */
public class DimenGenerator {

    /**
     * 设计稿尺寸(将自己设计师的设计稿的宽度填入)
     */
    private static final int DESIGN_WIDTH = 375;

    /**
     * 设计稿的高度  （将自己设计师的设计稿的高度填入）
     */
    private static final int DESIGN_HEIGHT = 667;

    /**
     * 执行lib module中的DimenGenerator.main()方法，当前地址下会生成相应的适配文件,把相应的文件连带文件夹拷贝到正在开发的项目中
     *
     * @param args
     */
    public static void main(String[] args) {
        int smallest = DESIGN_WIDTH > DESIGN_HEIGHT ? DESIGN_HEIGHT : DESIGN_WIDTH;  //     求得最小宽度
        DimenTypes[] values = DimenTypes.values();
        for (DimenTypes value : values) {
            File file = new File("");
            MakeUtils.makeAll(smallest, value, file.getAbsolutePath());
        }

    }

}
