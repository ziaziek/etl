/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ftpclientgui.DefaultFilesListRenderer;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Przemo
 */
public class DefaultListREndererTests {
    
    private class RendererStub extends DefaultFilesListRenderer{

        public RendererStub(JPanel renderedPanel) {
            super(renderedPanel);
        }
        
        public int getCurrentX(){
            return currentX;
        }
        
        public int getCurrentY(){
            return currentY;
        }
        
        public int getNumberOfItems(){
            if(rPan!=null){
                return rPan.getComponents().length;
            } else {
                return 0;
            }
        }
        
        public Component getComponent(int index){
            return rPan.getComponent(index);
        }
        
    }
    RendererStub r = new RendererStub(new JPanel(new FlowLayout()));
    
    public DefaultListREndererTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void renderTest(){
        r.render(new String[]{"A", "B"}, new String[]{"C", "D"});
        Assert.assertEquals(4, r.getNumberOfItems());
        Assert.assertEquals(0, r.getComponent(1).getX());
        Assert.assertEquals(0, r.getComponent(2).getX());
        Assert.assertTrue(r.getComponent(1).getY()>0);
        Assert.assertTrue(r.getComponent(0).getY()==0);
    }

}
