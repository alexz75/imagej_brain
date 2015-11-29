package il.ac.weizmann.brain.imagej;

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import ij.ImagePlus;
import ij.WindowManager;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import net.imagej.ImageJ;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * A command that generates a diagonal gradient image of user-given size.
 * <p>
 * For an even simpler command, see {@link HelloWorld} in this same package!
 * </p>
 */
@Plugin(type = Command.class, headless = true,
        menuPath = "Process>HSB Adjust")
public class HSBChangePlugin implements Command {

//    @Parameter
//    private DatasetService datasetService;

    @Parameter(min = "0", max = "180", description = "Hue adjust", label = "Hue+")
    private int hAdjust = 0;
    @Parameter(min = "0", max = "180", description = "Saturation adjust", label = "Saturation+")
    private int sAdjust = 0;
    @Parameter(min = "0", max = "180", description = "Brithness adjust", label = "Brithness+")
    private int bAdjust = 0;
//	@Parameter(type = ItemIO.OUTPUT)
//	private Dataset dataset;
    @Override
    public void run() {
        //we're replacing all colors in the picture
        ImagePlus image = WindowManager.getCurrentImage();
        // int[] dimensions = image.getDimensions();
        ImageProcessor processor = image.getProcessor();
        //int[][] imgArray = processor.getIntArray();
        ColorProcessor cp;
        if (processor instanceof ColorProcessor) {
            cp = (ColorProcessor) processor;
        } else {
            cp = (ColorProcessor) processor.convertToRGB();
        }
        int width1 = image.getWidth();
        int height1 = image.getHeight();
        int imgSize = width1 * height1;
        byte[] h = new byte[imgSize];
        byte[] s = new byte[imgSize];
        byte[] b = new byte[imgSize];
        cp.getHSB(h, s, b);
        for (int i = 0; i < imgSize; i++) {
            h[i] += hAdjust;
            s[i] += sAdjust;
            b[i] += bAdjust;
        }
        cp.setHSB(h, s, b);
        //processor.setIntArray(imgArray);
        image.updateAndRepaintWindow();

        // NB: Because the dataset is declared as an "OUTPUT" above,
        // ImageJ automatically takes care of displaying it afterwards!
    }

    /**
     * Tests our command.
     */
    public static void main(final String... args) throws Exception {
        // Launch ImageJ as usual.
        final ImageJ ij = net.imagej.Main.launch(args);

        // Launch the "Gradient Image" command right away.
        //	ij.command().run(GradientImage.class, true);
    }

}
