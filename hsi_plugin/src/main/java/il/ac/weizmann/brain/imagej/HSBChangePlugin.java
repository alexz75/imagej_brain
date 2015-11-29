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
import ij.plugin.filter.PlugInFilter;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import net.imagej.ImageJ;
import org.scijava.command.Command;
import org.scijava.command.Previewable;
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
public class HSBChangePlugin implements Command, Previewable {

//    @Parameter
//    private DatasetService datasetService;
    @Parameter
    private ImagePlus image;

    @Parameter(min = "0", max = "180", description = "Hue adjust", label = "Hue+")
    private byte hAdjust = 0;
    @Parameter(min = "0", max = "180", description = "Saturation adjust", label = "Saturation+")
    private byte sAdjust = 0;
    @Parameter(min = "0", max = "180", description = "Brithness adjust", label = "Brithness+")
    private byte bAdjust = 0;

    private byte[] originalH;
    private byte[] originalS;
    private byte[] originalB;

    @Override
    public void run() {

        ColorProcessor cp = getColorModel();
        int width = image.getWidth();
        int height = image.getHeight();
        int imgSize = width * height;
        boolean shouldLoad = false;
        if (originalH == null) {
            originalH = new byte[imgSize];
            shouldLoad = true;
        }
        if (originalS == null) {
            originalS = new byte[imgSize];
            shouldLoad = true;
        }
        if (originalB == null) {
            originalB = new byte[imgSize];
            shouldLoad = true;
        }
        if (shouldLoad) {
            cp.getHSB(originalH, originalS, originalB);
        }
        byte[] h = new byte[imgSize];
        byte[] s = new byte[imgSize];
        byte[] b = new byte[imgSize];

        for (int i = 0; i < imgSize; i++) {
            h[i] = (byte) (originalH[i] + hAdjust);
            s[i] = (byte) (originalS[i] + sAdjust);
            b[i] = (byte) (originalB[i] + bAdjust);
        }
        cp.setHSB(h, s, b);
        //processor.setIntArray(imgArray);
        image.updateAndRepaintWindow();

        // NB: Because the dataset is declared as an "OUTPUT" above,
        // ImageJ automatically takes care of displaying it afterwards!
    }

    private ColorProcessor getColorModel() {
        //we're replacing all colors in the picture
        // int[] dimensions = image.getDimensions();

        ImageProcessor processor = image.getProcessor();
        //int[][] imgArray = processor.getIntArray();
        ColorProcessor cp;
        if (processor instanceof ColorProcessor) {
            cp = (ColorProcessor) processor;
        } else {
            cp = (ColorProcessor) processor.convertToRGB();
        }
        return cp;
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

    @Override
    public void preview() {
        run();
    }

    @Override
    public void cancel() {

        ColorProcessor cp = getColorModel();
        cp.setHSB(originalH, originalS, originalB);
        image.updateAndRepaintWindow();

    }

}
