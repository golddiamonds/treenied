import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;


public class Skeleton {

	
	float x_block;
	float y_block;
	float z_block;
	int hp;

	
	public Skeleton(int hpstart, float xpoint, float ypoint, float zpoint){
		x_block = xpoint;
		y_block = ypoint;
		z_block = zpoint;
		hp = hpstart;
	}
	
	public void drawSkeleton(){
		// Start of mouse - LEFT - Green
				glBegin(GL_POLYGON);
				glColor3f(   1.0f,1.0f,1.0f );
				glVertex3f( -1.0f + x_block, 0.0f + y_block,  0.5f + z_block );
				glVertex3f( -1.0f + x_block,  3.0f + y_block,  0.5f + z_block );
				glVertex3f( -1.0f + x_block,  3.0f + y_block, 0.0f + z_block );
				glVertex3f( -1.0f + x_block, 0.0f + y_block, 0.0f + z_block );
				glEnd();
				
				// mouse - RIGHT - Green
				glBegin(GL_POLYGON);
				glColor3f(   1.0f,1.0f,1.0f );
				glVertex3f( -0.5f + x_block, 0.0f + y_block,  0.5f + z_block );
				glVertex3f( -0.5f + x_block,  3.0f + y_block,  0.5f + z_block );
				glVertex3f( -0.5f + x_block,  3.0f + y_block, 0.0f + z_block );
				glVertex3f( -0.5f + x_block, 0.0f + y_block, 0.0f + z_block );
				glEnd();
				
				// mouse - BACK - green
				glBegin(GL_POLYGON);
				glColor3f(   1.0f,1.0f,1.0f );
				glVertex3f( -0.5f + x_block, 0.0f + y_block,  0.5f + z_block );
				glVertex3f( -0.5f + x_block,  3.0f + y_block,  0.5f + z_block );
				glVertex3f( -1.0f + x_block,  3.0f + y_block, 0.5f + z_block );
				glVertex3f( -1.0f + x_block, 0.0f + y_block, 0.5f + z_block );
				glEnd();
				
				//mouse - FRONT - green
				glBegin(GL_POLYGON);
				glColor3f(   1.0f,1.0f,1.0f );
				glVertex3f( -0.5f + x_block, 0.0f + y_block,  0.0f + z_block );
				glVertex3f( -0.5f + x_block,  3.0f + y_block,  0.0f + z_block );
				glVertex3f( -1.0f + x_block,  3.0f + y_block, 0.0f + z_block );
				glVertex3f( -1.0f + x_block, 0.0f + y_block, 0.0f + z_block );
				glEnd();
				
				//mouse - TOP - green
				glBegin(GL_POLYGON);
				glColor3f(   1.0f,1.0f,1.0f );
				glVertex3f( -0.5f + x_block, 0.0f + y_block,  0.0f + z_block );
				glVertex3f( -0.5f + x_block,  0.0f + y_block,  0.5f + z_block );
				glVertex3f( -1.0f + x_block,  0.0f + y_block, 0.5f + z_block );
				glVertex3f( -1.0f + x_block, 0.0f + y_block, 0.0f + z_block );
				glEnd();
				
				//mouse -  - green
				glBegin(GL_POLYGON);
				glColor3f(   1.0f,1.0f,1.0f );
				glVertex3f( -0.5f + x_block, 3.0f + y_block,  0.0f + z_block );
				glVertex3f( -0.5f + x_block,  3.0f + y_block,  0.5f + z_block );
				glVertex3f( -1.0f + x_block,  3.0f + y_block, 0.5f + z_block );
				glVertex3f( -1.0f + x_block, 3.0f + y_block, 0.0f + z_block );
				glEnd();
	}
	public float skeletonDamage(float x, float z){
		if (Math.abs(x - x_block) < 5 && Math.abs(z - z_block) < 5){
			return .02f;
		}else {
			return 0.0f;
		}
	}
	
}
