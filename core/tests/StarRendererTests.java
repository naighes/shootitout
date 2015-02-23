
import com.nukedbit.shootitout.StarLayer;
import com.nukedbit.shootitout.StarLayerManager;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by sebastian on 23/02/15.
 */
public class StarRendererTests {

    @Test
    public void RenderStarLayers() {

        final int delta = 10;
        final StarLayer starLayer1 = mock(StarLayer.class);
        final StarLayer starLayer2 = mock(StarLayer.class);

        doNothing().when(starLayer1).render(delta);
        doNothing().when(starLayer2).render(delta);

        final StarLayerManager renderer = new StarLayerManager(CreateStarLayerList(starLayer1, starLayer2));
        renderer.Render(delta);

        InOrder inOrder = inOrder(starLayer1,starLayer2);
        inOrder.verify(starLayer1).render(delta);
        inOrder.verify(starLayer2).render(delta);

    }

    private List<StarLayer> CreateStarLayerList(StarLayer... starLayers) {
        return Arrays.asList(starLayers);
    }
}
