import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;


public class Block {
	
	float x_block;
	float y_block;
	float z_block;
	float r_block;
	float g_block;
	float b_block;
	
	public Block(float xpoint, float ypoint, float zpoint,
			float rpoint, float bpoint, float gpoint){
		x_block = xpoint;
		y_block = ypoint;
		z_block = zpoint;
		r_block = rpoint;
		b_block = bpoint;
		g_block = gpoint;
	}
	
	public void drawBlock(){
		// Start of mouse - LEFT - Green
		glBegin(GL_POLYGON);
		glColor3f(   r_block, b_block, g_block );
		glVertex3f( -1.0f + x_block, 0.0f + y_block,  0.5f + z_block );
		glVertex3f( -1.0f + x_block,  0.5f + y_block,  0.5f + z_block );
		glVertex3f( -1.0f + x_block,  0.5f + y_block, 0.0f + z_block );
		glVertex3f( -1.0f + x_block, 0.0f + y_block, 0.0f + z_block );
		glEnd();
		
		// mouse - RIGHT - Green
		glBegin(GL_POLYGON);
		glColor3f(   r_block, b_block, g_block );
		glVertex3f( -0.5f + x_block, 0.0f + y_block,  0.5f + z_block );
		glVertex3f( -0.5f + x_block,  0.5f + y_block,  0.5f + z_block );
		glVertex3f( -0.5f + x_block,  0.5f + y_block, 0.0f + z_block );
		glVertex3f( -0.5f + x_block, 0.0f + y_block, 0.0f + z_block );
		glEnd();
		
		// mouse - BACK - green
		glBegin(GL_POLYGON);
		glColor3f(   r_block, b_block, g_block );
		glVertex3f( -0.5f + x_block, 0.0f + y_block,  0.5f + z_block );
		glVertex3f( -0.5f + x_block,  0.5f + y_block,  0.5f + z_block );
		glVertex3f( -1.0f + x_block,  0.5f + y_block, 0.5f + z_block );
		glVertex3f( -1.0f + x_block, 0.0f + y_block, 0.5f + z_block );
		glEnd();
		
		//mouse - FRONT - green
		glBegin(GL_POLYGON);
		glColor3f(   r_block, b_block, g_block );
		glVertex3f( -0.5f + x_block, 0.0f + y_block,  0.0f + z_block );
		glVertex3f( -0.5f + x_block,  0.5f + y_block,  0.0f + z_block );
		glVertex3f( -1.0f + x_block,  0.5f + y_block, 0.0f + z_block );
		glVertex3f( -1.0f + x_block, 0.0f + y_block, 0.0f + z_block );
		glEnd();
		
		//mouse - TOP - green
		glBegin(GL_POLYGON);
		glColor3f(   r_block, b_block, g_block );
		glVertex3f( -0.5f + x_block, 0.0f + y_block,  0.0f + z_block );
		glVertex3f( -0.5f + x_block,  0.0f + y_block,  0.5f + z_block );
		glVertex3f( -1.0f + x_block,  0.0f + y_block, 0.5f + z_block );
		glVertex3f( -1.0f + x_block, 0.0f + y_block, 0.0f + z_block );
		glEnd();
		
		//mouse -  - green
		glBegin(GL_POLYGON);
		glColor3f(   r_block, b_block, g_block );
		glVertex3f( -0.5f + x_block, 0.5f + y_block,  0.0f + z_block );
		glVertex3f( -0.5f + x_block,  0.5f + y_block,  0.5f + z_block );
		glVertex3f( -1.0f + x_block,  0.5f + y_block, 0.5f + z_block );
		glVertex3f( -1.0f + x_block, 0.5f + y_block, 0.0f + z_block );
		glEnd();
	}
	
}
