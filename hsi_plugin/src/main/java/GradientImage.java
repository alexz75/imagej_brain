/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.process.ImageProcessor;
import java.awt.Color;
import net.imagej.Dataset;
import net.imagej.DatasetService;
import net.imagej.ImageJ;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * A command that generates a diagonal gradient image of user-given size.
 * <p>
 * For an even simpler command, see {@link HelloWorld} in this same
 * package!
 * </p>
 */
@Plugin(type = Command.class, headless = true,
	menuPath = "File>New>Gradient Image")
public class GradientImage implements Command {
        
	@Parameter
	private DatasetService datasetService;

	@Parameter(min = "1")
	private int width = 512;

	@Parameter(min = "1")
	private int height = 512;

//	@Parameter(type = ItemIO.OUTPUT)
//	private Dataset dataset;

	@Override
	public void run() {
            //we're replacing all colors in the picture
            ImagePlus image=WindowManager.getCurrentImage();
            // int[] dimensions = image.getDimensions();
            ImageProcessor processor = image.getProcessor();
            int[][] imgArray = processor.getIntArray();
            
            for(int x=0; x<imgArray.length; x++){
                for(int y=0; y<imgArray[x].length; y++){
                    imgArray[x][y]=Color.red.getRGB();
                }
            }
        
            processor.setIntArray(imgArray);
            image.updateAndRepaintWindow();
                
		// NB: Because the dataset is declared as an "OUTPUT" above,
		// ImageJ automatically takes care of displaying it afterwards!
	}

	/** Tests our command. */
	public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		final ImageJ ij = net.imagej.Main.launch(args);

		// Launch the "Gradient Image" command right away.
	//	ij.command().run(GradientImage.class, true);
	}

}
