
/*
 * IMPORTS FROM: 	lwjgl.jar + Native Libraries
 * 					lwjgl_util.jar
 * 					slick-util.jar
 */

import static org.lwjgl.opengl.GL11.GL_POLYGON;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFlush;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.GLU.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.Color;

import java.awt.Font;
import java.io.InputStream;
import java.nio.FloatBuffer;

import java.util.*;
import org.lwjgl.util.glu.Sphere;

public class Game {

	final static int width = 800, height = 600;
	final static int frameRate = 90;
	boolean[] keys = new boolean[256];
	float x_box = 1.0f;
	static float x = 0.0f;
	static float y = 0.0f;
	static float z = 0.0f;
	static float xrot = 0.0f;
	static float yrot = 0.0f;
	static float zrot = 0.0f;
	float mouse_x = 50.0f;
	float mouse_z = 50.0f;
	float mouse_y = 0.0f;
	float armx = 0.0f;
	float army = 0.0f;
	float armz = 0.0f;
	static float hp = 10.0f;
	Camera camera;
	Texture texWhite;
	Texture texFloor;
	//make a 10 skeleton object array
	static Skeleton[] skeletonArray = new Skeleton[10];
	
	
	
	public static void main(String[] args) throws LWJGLException {
		skeletonArray[1] = new Skeleton(10,30f,0f,20f);
		Display.setDisplayMode(new DisplayMode(width,height));
		String title_value = "init";
		Display.setTitle(title_value);
		Display.create();
		Game game = new Game();
		game.init3D();
		while(!Display.isCloseRequested() &&
				!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			game.render();
			game.update();
			title_value = String.format("%.2f", x) + " " + 
						  String.format("%.2f", y) + " " + 
						  String.format("%.2f", z) + " HP: " +
						  String.format("%.2f", hp) + " " + 
						  String.format("%.2f", xrot) + " " + 
						  String.format("%.2f",yrot) + " " + 
						  String.format("%.2f", zrot) ;
			hp = hp - skeletonArray[1].skeletonDamage(x, z);
			Display.setTitle(title_value);
			Display.update();
			Display.sync(frameRate);
		
		}
		Display.destroy();
		System.exit(0);
	}
	
	public Game(){
		camera = new Camera(this);
		loadTextures();
	}
	
	private void loadTextures(){
		try{
			texWhite = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/white.jpg"));
			texFloor = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/floor.jpg"));
		}catch(Exception e){
			System.err.println("Failed to load texture: ");
			e.printStackTrace();
		}
	}
	
	public void render(){
		clearScreen();
		x = camera.x;
		armx = camera.x;
		xrot = camera.rotation.x;
		x = Math.round(x * 100.0f)/100.0f;
		y = camera.y;
		yrot = camera.rotation.y;
		army = camera.y + 1.4f;
		y = Math.round(y * 100.0f)/100.0f;
		z = camera.z;
		armz = camera.z;
		zrot = camera.rotation.z;
		z = Math.round(z * 100.0f)/100.0f;
		
		camera.translatePostion();
		
		glColor3f(1.0f,1.0f,1.0f);
		//Render a textured rectangular floor at 0,0 to 10,10
		texFloor.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(0, 0, 0);
		
		GL11.glTexCoord2f(10, 0);
		GL11.glVertex3f(100, 0, 0);
		
		GL11.glTexCoord2f(10, 10);
		GL11.glVertex3f(100, 0, 100);
		
		GL11.glTexCoord2f(0, 10);
		GL11.glVertex3f(0, 0, 100);
		GL11.glEnd();
		
		texFloor.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(0, 10, 0);
		
		GL11.glTexCoord2f(10, 0);
		GL11.glVertex3f(100, 10, 0);
		
		GL11.glTexCoord2f(10, 10);
		GL11.glVertex3f(100, 10, 100);
		
		GL11.glTexCoord2f(0, 10);
		GL11.glVertex3f(0, 10, 100);
		GL11.glEnd();
		
		//SPHERE test
		FloatBuffer matSpecular;
		FloatBuffer lightPosition;
		FloatBuffer whiteLight;
		FloatBuffer lModelAmbient;
		
		matSpecular = BufferUtils.createFloatBuffer(4);
		matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();
		
		lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(1.0f).put(3.0f).put(1.0f).put(0.0f).flip();
		
		whiteLight = BufferUtils.createFloatBuffer(4);
		whiteLight.put(1.0f).put(1.0f).put(1.0f).put(.5f).flip();
		
		lModelAmbient = BufferUtils.createFloatBuffer(4);
		lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(.5f).flip();
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glMaterial(GL11.GL_FRONT,GL11.GL_SPECULAR, matSpecular);
		GL11.glMaterialf(GL11.GL_FRONT,  GL11.GL_SHININESS, 50.0f);
		
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition);
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, whiteLight);
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, whiteLight);
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, lModelAmbient);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);
		
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE);
		
		glColor3f(1.0f,0.0f,0.0f);
		Sphere s = new Sphere();
		s.draw(2.4f, 16, 16);

		
		// White side - BACK
				glBegin(GL_POLYGON);
				glColor3f(   1.0f,  1.0f, 1.0f );
				glVertex3f(  0.5f + .5f, -0.5f + .5f, 0.5f + .5f );
				glVertex3f(  0.5f + .5f,  0.5f + .5f, 0.5f + .5f );
				glVertex3f( -0.5f + .5f,  0.5f + .5f, 0.5f + .5f );
				glVertex3f( -0.5f + .5f, -0.5f + .5f, 0.5f + .5f );
				glEnd();
				 
				// Purple side - RIGHT
				glBegin(GL_POLYGON);
				glColor3f(  1.0f,  0.0f,  1.0f );
				glVertex3f( 0.5f + .5f, -0.5f + .5f, -0.5f + .5f );
				glVertex3f( 0.5f + .5f,  0.5f + .5f, -0.5f + .5f );
				glVertex3f( 0.5f + .5f,  0.5f + .5f,  0.5f + .5f );
				glVertex3f( 0.5f + .5f, -0.5f + .5f,  0.5f + .5f );
				glEnd();
				 
				// Green side - LEFT
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  1.0f,  0.0f );
				glVertex3f( -0.5f + .5f, -0.5f + .5f,  0.5f + .5f );
				glVertex3f( -0.5f + .5f,  0.5f + .5f,  0.5f + .5f );
				glVertex3f( -0.5f + .5f,  0.5f + .5f, -0.5f + .5f );
				glVertex3f( -0.5f + .5f, -0.5f + .5f, -0.5f + .5f );
				glEnd();
				 
				// Blue side - TOP
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  0.0f,  1.0f);
				glVertex3f(  0.5f + .5f,  0.5f + .5f,  0.5f + .5f );
				glVertex3f(  0.5f + .5f,  0.5f + .5f, -0.5f + .5f );
				glVertex3f( -0.5f + .5f,  0.5f + .5f, -0.5f + .5f );
				glVertex3f( -0.5f + .5f,  0.5f + .5f,  0.5f + .5f );
				glEnd();
				 
				// Red side - BOTTOM
				glBegin(GL_POLYGON);
				glColor3f(   1.0f,  0.0f,  0.0f );
				glVertex3f(  0.5f + .5f, -0.5f + .5f, -0.5f + .5f );
				glVertex3f(  0.5f + .5f, -0.5f + .5f,  0.5f + .5f );
				glVertex3f( -0.5f + .5f, -0.5f + .5f,  0.5f + .5f );
				glVertex3f( -0.5f + .5f, -0.5f + .5f, -0.5f + .5f );
				glEnd();	
							
				
				while (x_box < 100.0f){
				
				// White side - BACK
				glBegin(GL_POLYGON);
				glColor3f(   1.0f,  1.0f, 1.0f );
				glVertex3f(  0.5f + .5f + x_box, -0.5f + .5f, 0.5f + .5f );
				glVertex3f(  0.5f + .5f + x_box,  0.5f + .5f, 0.5f + .5f );
				glVertex3f( -0.5f + .5f + x_box,  0.5f + .5f, 0.5f + .5f );
				glVertex3f( -0.5f + .5f + x_box, -0.5f + .5f, 0.5f + .5f );
				glEnd();
				 
				// Purple side - RIGHT
				glBegin(GL_POLYGON);
				glColor3f(  1.0f,  0.0f,  1.0f );
				glVertex3f( 0.5f + .5f + x_box, -0.5f + .5f, -0.5f + .5f );
				glVertex3f( 0.5f + .5f + x_box,  0.5f + .5f, -0.5f + .5f );
				glVertex3f( 0.5f + .5f + x_box,  0.5f + .5f,  0.5f + .5f );
				glVertex3f( 0.5f + .5f + x_box, -0.5f + .5f,  0.5f + .5f );
				glEnd();
				 
				// Green side - LEFT
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  1.0f,  0.0f );
				glVertex3f( -0.5f + .5f + x_box, -0.5f + .5f,  0.5f + .5f );
				glVertex3f( -0.5f + .5f + x_box,  0.5f + .5f,  0.5f + .5f );
				glVertex3f( -0.5f + .5f + x_box,  0.5f + .5f, -0.5f + .5f );
				glVertex3f( -0.5f + .5f + x_box, -0.5f + .5f, -0.5f + .5f );
				glEnd();
				 
				// Blue side - TOP
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  0.0f,  1.0f);
				glVertex3f(  0.5f + .5f + x_box,  0.5f + .5f,  0.5f + .5f );
				glVertex3f(  0.5f + .5f + x_box,  0.5f + .5f, -0.5f + .5f );
				glVertex3f( -0.5f + .5f + x_box,  0.5f + .5f, -0.5f + .5f );
				glVertex3f( -0.5f + .5f + x_box,  0.5f + .5f,  0.5f + .5f );
				glEnd();
				 
				// Red side - BOTTOM
				glBegin(GL_POLYGON);
				glColor3f(   1.0f,  0.0f,  0.0f );
				glVertex3f(  0.5f + .5f + x_box, -0.5f + .5f, -0.5f + .5f );
				glVertex3f(  0.5f + .5f + x_box, -0.5f + .5f,  0.5f + .5f );
				glVertex3f( -0.5f + .5f + x_box, -0.5f + .5f,  0.5f + .5f );
				glVertex3f( -0.5f + .5f + x_box, -0.5f + .5f, -0.5f + .5f );
				glEnd();	
				
				x_box += 1.0f;
						
				}
				x_box = 1.0f;
				
				//instantiate block class at 10,0,10 w/ black
				Block block1 = new Block(10.0f,0f,10.0f, 0.0f,0.0f,0.0f);
				//draw block instance
				block1.drawBlock();
				//instantiate block class at 11,0,11 w/ gray
				Block block2 = new Block(11.0f,0f,10.0f,1.0f,1.0f,1.0f);
				//draw block instance
				block2.drawBlock();
				//instantiate skeleton class at 15,0,15
				Skeleton skeleton1 = new Skeleton(10, 15.0f,0.0f,15.0f);
				//draw skeleton instance
				skeleton1.drawSkeleton();
				
				//try skeleton array
				skeletonArray[1].drawSkeleton();
				
				
				
				// Start of mouse - LEFT - Green
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  1.0f,  0.0f );
				glVertex3f( -1.0f + mouse_x, 0.0f + mouse_y,  0.5f + mouse_z );
				glVertex3f( -1.0f + mouse_x,  0.5f + mouse_y,  0.5f + mouse_z );
				glVertex3f( -1.0f + mouse_x,  0.5f + mouse_y, 0.0f + mouse_z );
				glVertex3f( -1.0f + mouse_x, 0.0f + mouse_y, 0.0f + mouse_z );
				glEnd();
				
				// mouse - RIGHT - Green
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  1.0f,  0.0f );
				glVertex3f( -0.5f + mouse_x, 0.0f + mouse_y,  0.5f + mouse_z );
				glVertex3f( -0.5f + mouse_x,  0.5f + mouse_y,  0.5f + mouse_z );
				glVertex3f( -0.5f + mouse_x,  0.5f + mouse_y, 0.0f + mouse_z );
				glVertex3f( -0.5f + mouse_x, 0.0f + mouse_y, 0.0f + mouse_z );
				glEnd();
				
				// mouse - BACK - green
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  1.0f,  0.0f );
				glVertex3f( -0.5f + mouse_x, 0.0f + mouse_y,  0.5f + mouse_z );
				glVertex3f( -0.5f + mouse_x,  0.5f + mouse_y,  0.5f + mouse_z );
				glVertex3f( -1.0f + mouse_x,  0.5f + mouse_y, 0.5f + mouse_z );
				glVertex3f( -1.0f + mouse_x, 0.0f + mouse_y, 0.5f + mouse_z );
				glEnd();
				
				//mouse - FRONT - green
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  1.0f,  0.0f );
				glVertex3f( -0.5f + mouse_x, 0.0f + mouse_y,  0.0f + mouse_z );
				glVertex3f( -0.5f + mouse_x,  0.5f + mouse_y,  0.0f + mouse_z );
				glVertex3f( -1.0f + mouse_x,  0.5f + mouse_y, 0.0f + mouse_z );
				glVertex3f( -1.0f + mouse_x, 0.0f + mouse_y, 0.0f + mouse_z );
				glEnd();
				
				//mouse - TOP - green
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  1.0f,  0.0f );
				glVertex3f( -0.5f + mouse_x, 0.0f + mouse_y,  0.0f + mouse_z );
				glVertex3f( -0.5f + mouse_x,  0.0f + mouse_y,  0.5f + mouse_z );
				glVertex3f( -1.0f + mouse_x,  0.0f + mouse_y, 0.5f + mouse_z );
				glVertex3f( -1.0f + mouse_x, 0.0f + mouse_y, 0.0f + mouse_z );
				glEnd();
				
				//mouse -  - green
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  1.0f,  0.0f );
				glVertex3f( -0.5f + mouse_x, 0.5f + mouse_y,  0.0f + mouse_z );
				glVertex3f( -0.5f + mouse_x,  0.5f + mouse_y,  0.5f + mouse_z );
				glVertex3f( -1.0f + mouse_x,  0.5f + mouse_y, 0.5f + mouse_z );
				glVertex3f( -1.0f + mouse_x, 0.5f + mouse_y, 0.0f + mouse_z );
				glEnd();
				
				//move mouse
				if (mouse_x > x){
					mouse_x -= .05f;
				}
				if (mouse_x < x){
					mouse_x += .05f;
				}
				if (mouse_z > z){
					mouse_z -= .05f;
				}
				if (mouse_z < z){
					mouse_z += .05f;
				}
				
				if (Math.abs(mouse_x - x) < 5 && Math.abs(mouse_z - z) < 5){
					mouse_y = 1.0f;
				} else {
					mouse_y = 0.0f;
				}
				
				
				GL11.glTranslatef(armx, army, armz);
				GL11.glRotatef(camera.rotation.x, 1, 0, 0);
				GL11.glRotatef(-camera.rotation.y, 0, 1, 0);
				GL11.glRotatef(camera.rotation.z, 0, 0, 1);
				//ARM - want to rotate then translate this square...
				// Start of mouse - LEFT - Green
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  1.0f,  0.0f );
				glVertex3f( 1.0f, 0.0f ,  -1f );
				glVertex3f( 1.0f,  0.5f , -1f );
				glVertex3f( 1.0f,  0.5f , -0.0f  );
				glVertex3f( 1.0f , 0.0f, -0.0f  );
				glEnd();
				
				// mouse - RIGHT - Green
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  1.0f,  0.0f );
				glVertex3f( 0.5f  , 0.0f  ,  -1f   );
				glVertex3f( 0.5f  ,  0.5f  ,  -1f  );
				glVertex3f( 0.5f ,  0.5f  , -0.0f   );
				glVertex3f( 0.5f  , 0.0f  , -0.0f   );
				glEnd();
				
				// mouse - BACK - green
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  1.0f,  0.0f );
				glVertex3f( 0.5f  , 0.0f  ,  -1f   );
				glVertex3f( 0.5f  ,  0.5f  , -1f   );
				glVertex3f( 1.0f  ,  0.5f  , -1f   );
				glVertex3f( 1.0f  , 0.0f  , -1f   );
				glEnd();
				
				//mouse - FRONT - green
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  1.0f,  0.0f );
				glVertex3f( 0.5f  , 0.0f  ,  -.0f   );
				glVertex3f( 0.5f  ,  0.5f  ,  -0.0f   );
				glVertex3f( 1.0f  ,  0.5f  , -.0f   );
				glVertex3f( 1.0f  , 0.0f  , -0.0f   );
				glEnd();
				
				//mouse - TOP - green
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  1.0f,  0.0f );
				glVertex3f( 0.5f  , 0.0f  ,  -0.0f   );
				glVertex3f( 0.5f  ,  0.0f  ,  -1f   );
				glVertex3f( 1.0f  ,  0.0f  , -1f   );
				glVertex3f( 1.0f  , 0.0f  , -0.0f   );
				glEnd();
				
				//mouse -  - green
				glBegin(GL_POLYGON);
				glColor3f(   0.0f,  1.0f,  0.0f );
				glVertex3f( 0.5f  , 0.5f  ,  -0.0f   );
				glVertex3f( 0.5f  ,  0.5f  ,  -1f   );
				glVertex3f( 1.0f  ,  0.5f  , -1f   );
				glVertex3f( 1.0f  , 0.5f  , -0.0f   );
				glEnd();
				
				//crosshair
				GL11.glViewport(0, 0, width, height);
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glOrtho(0f, width, 0f, height,-1f,1f);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();
				GL11.glLineWidth(1.0f);
				GL11.glColor3f(1f, 1f, 1f);
				
				glBegin(GL11.GL_LINES);
					GL11.glVertex3f((width/2),(height/2) +10.0f,0f);
					GL11.glVertex3f((width/2),(height/2) - 10.0f,0f);
				glEnd();
				glBegin(GL11.GL_LINES);
					GL11.glVertex3f((width/2) + 10.0f,(height/2),0f);
					GL11.glVertex3f((width/2) - 10.0f,(height/2),0f);
				glEnd();
				/* MAY EVENTUALLY USE FOR A TEXT BOX or something...
				//try a box
				glBegin(GL11.GL_POLYGON);
				glVertex3f( (width/2), 100,  0.0f );
				glVertex3f( width/3,  100,  0.0f );
				glVertex3f( width/3,  10, 0.0f );
				glVertex3f( width/2, 10, 0.0f );
				glEnd();
				glBegin(GL11.GL_POLYGON);
				glVertex3f( (width/2) + 10, 100 + 10,  1.0f );
				glVertex3f( width/3 + 10,  100 + 10,  1.0f );
				glVertex3f( width/3 + 10,  10 + 10, 1.0f );
				glVertex3f( width/2 + 10, 10 + 10, 1.0f );
				glEnd();
				glBegin(GL11.GL_POLYGON);
				glVertex3f( (width/2) + 10, 100 + 10,  0.0f );
				glVertex3f( width/3 + 10,  100 + 10,  1.0f );
				glVertex3f( width/3 + 10,  10 + 10, 1.0f );
				glVertex3f( width/2 + 10, 10 + 10, 0.0f );
				glEnd();
				*/
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				//flush commands hanging about
						glFlush();
				init3D();
	}
	
	public void update(){
		mapKeys();
		camera.update();
		
	}
	
	private void mapKeys(){
		//Update keys
		for(int i=0;i<keys.length;i++){
			keys[i] = Keyboard.isKeyDown(i);
		}
	}
	
	public void init3D(){
		//Start 3D Stuff
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GLU.gluPerspective((float) 100, width / height, 0.001f, 1000);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		GL11.glClearDepth(1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
	}
	
	public void clearScreen(){
		//Clear the screen
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
	}
}
