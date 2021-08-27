import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Game extends JPanel implements ActionListener {

	public Pacman pacman; 
	private Ghost[] ghost;
	private boolean started = false, gameover = false, buttonFlag = true, buttonFlag2 = true, timerFlag = true,
			completedFlag = true;
	private final int BLOCK_SIZE = 48, N_BLOCKS = 15, SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
	private int N_GHOSTS = 4, lives = 3, score = 0, remainingTime = 90, level = 0, levelCheck;
	public int dx, dy; 
	private Image heartImage, ghostImage, pacmanUp, pacmanDown, pacmanLeft, pacmanRight, beginImage1, beginImage2,
			gameoverImage,winImage;
	private String name = "";
	private Random random = new Random();
	private int maze[][] = new int[3][]; 
	private int[] levelScore = new int[3];
	// 0 Bait
	// 3 Big Bait
	// 2 Empty
	// 6 Wall
	// 11 Ghost Box
	// 1 Border horizontal top
	// 16 Border vertical Left
	// 21 Border vertical Right
	// 26 Border Corner
	// 31 Border horizontal bottom
	private final int[] level1 = { 26, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 26, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 21, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 3, 0, 21, 16, 0, 6, 3, 0, 0, 0, 0, 0, 0, 6, 6, 6, 0, 21, 16,
			0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 21, 16, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 16, 0, 6, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 21, 16, 0, 6, 0, 0, 0, 11, 2, 11, 0, 0, 0, 0, 0, 21, 16, 0, 6, 0, 0, 0, 11, 11, 11,
			0, 0, 0, 0, 0, 21, 16, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 16, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			21, 2, 0, 0, 0, 6, 6, 6, 6, 0, 0, 6, 6, 0, 0, 2, 16, 0, 0, 0, 3, 0, 0, 0, 0, 0, 6, 6, 0, 0, 21, 16, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 26, 31, 31, 31, 31, 2, 31, 31, 31, 31, 31, 31, 31, 31, 26 };
	private final int[] level2 = { 26, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 26, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 21, 16, 0, 6, 6, 6, 6, 0, 0, 0, 0, 6, 6, 6, 0, 21, 16, 0, 6, 3, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 21, 16,
			0, 6, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 21, 16, 0, 6, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 21, 16, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 21, 16, 0, 0, 0, 0, 0, 11, 2, 11, 0, 0, 0, 0, 0, 21, 16, 0, 0, 0, 0, 0, 11, 11, 11,
			0, 0, 0, 0, 0, 21, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			21, 16, 0, 0, 0, 6, 6, 6, 0, 0, 0, 0, 0, 0, 0, 21, 16, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 16, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 26, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 26 };
	private final int[] level3 = { 26, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 26, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 21, 16, 0, 6, 6, 6, 6, 0, 0, 0, 0, 0, 6, 0, 0, 21, 16, 0, 6, 3, 0, 6, 0, 0, 0, 0, 6, 6, 6, 0, 21, 16,
			0, 6, 0, 0, 6, 0, 0, 0, 0, 0, 6, 0, 0, 21, 16, 0, 6, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 21, 16, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 21, 16, 0, 0, 0, 0, 0, 11, 2, 11, 0, 0, 0, 0, 0, 21, 16, 0, 0, 0, 0, 0, 11, 11, 11,
			0, 0, 0, 0, 0, 21, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			21, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 16, 0, 0, 0, 0, 0, 6, 6, 6, 0, 0, 0, 0, 0, 21, 16, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 26, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 26 };
	

	public Game() {
		Sound.beginningSound();

		pacman = new Pacman(7 * BLOCK_SIZE, 9 * BLOCK_SIZE, 0, 0);
		ghost = new Ghost[N_GHOSTS + 3];
		for (int i = 0; i < N_GHOSTS + 3; i++)
			ghost[i] = new Ghost(7 * BLOCK_SIZE, 7 * BLOCK_SIZE, 0, 0);
		
		//maze - level 
		maze[0] = Arrays.copyOf(level1, level1.length); 
		maze[1] = Arrays.copyOf(level2, level2.length);
		maze[2] = Arrays.copyOf(level3, level3.length);
		
		
		levelScore[0] = levelScore[1] = levelScore[2] =  0;

		for (int k = 0; k < 3; k++)
			for (int i = 0; i < N_BLOCKS; i++)
				for (int j = 0; j < N_BLOCKS; j++)
					if (maze[k][i + j * N_BLOCKS] == 0)
						levelScore[k]++;
					else if (maze[k][i + j * N_BLOCKS] == 3)
						levelScore[k] += 5;
		
		
		
		pacmanDown = new ImageIcon("res/gif/down.gif").getImage();
		pacmanUp = new ImageIcon("res/gif/up.gif").getImage();
		pacmanLeft = new ImageIcon("res/gif/left.gif").getImage();
		pacmanRight = new ImageIcon("res/gif/right.gif").getImage();
		ghostImage = new ImageIcon("res/gif/ghost.gif").getImage();
		heartImage = new ImageIcon("res/images/heart.png").getImage();
		beginImage1 = new ImageIcon("res/images/begin1.jpg").getImage();
		beginImage2 = new ImageIcon("res/images/begin2.jpg").getImage();
		gameoverImage = new ImageIcon("res/images/gameover.jpg").getImage();
		winImage = new ImageIcon("res/images/win.png").getImage();

		addKeyListener(new KeyManager(this));
		setFocusable(true); 
		setLayout(null); 
		continueGame();
		new javax.swing.Timer(500, this).start(); 
	}

	public void paintComponent(Graphics g) { 
		g.clearRect(0, 0, getWidth(), getHeight());

		if (!started && !gameover) { 
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(beginImage1, 100, 200, 520, 200, this);
			g.drawImage(beginImage2, 100, 0, 520, 200, this);

			if (buttonFlag) {
				JTextField nameField = new JTextField("Name");
				nameField.setFont(new Font("Tahoma", Font.ITALIC, 15));
				nameField.setHorizontalAlignment(SwingConstants.CENTER);
				nameField.setBounds(275, 445, 170, 50);
				nameField.addFocusListener(new FocusAdapter() { 
					@Override
					public void focusGained(FocusEvent e) {
						nameField.setText("");
						nameField.setFont(new Font("Tahoma", Font.BOLD, 20));
					}
				});

				JButton startButton = new JButton("Start Game!");
				startButton.setBounds(275, 500, 170, 50);

				startButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) { 
						name = nameField.getText();
						started = true;
						remove(nameField);
						remove(startButton);
						Sound.chompSound();
					}
				});

				add(nameField);
				add(startButton);
				buttonFlag = false;
			}
		} else if (started && !gameover) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());

			drawMaze(g);
			movePacman(g);
			moveGhosts(g);
			if (timerFlag)
				startCountdown();

			g.setFont(new Font("Tahoma", Font.BOLD, 15));
			g.setColor(Color.GREEN);
			String s = "Score: " + score;
			g.drawString(s, SCREEN_SIZE / 2 + 200, SCREEN_SIZE + 16);
			for (int i = 0; i < lives; i++) {
				g.drawImage(heartImage, i * 28 + 8, SCREEN_SIZE + 1, this);
			}
			g.drawString("Remaining Time: " + remainingTime, SCREEN_SIZE / 2, SCREEN_SIZE + 16);
			g.drawString("User: " + name, SCREEN_SIZE / 2 - 200, SCREEN_SIZE + 16);

			Toolkit.getDefaultToolkit().sync(); 
			g.dispose(); 
		} else if (started && gameover) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(gameoverImage, 0, 0, getWidth(), 500, this);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Tahoma", Font.BOLD, 40));
			g.drawString("Your Score: " + score, 200, 500);

			if (buttonFlag2) {
				JButton restartButton = new JButton("Restart The Game");
				restartButton.setBounds(275, 600, 170, 50);
				restartButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						started = true;
						gameover = false;
						lives = 3;
						pacman.setSpeed(8);
						N_GHOSTS = 4;
						remainingTime = 90;
						score = 0;
						level = 0;
						maze[0] = Arrays.copyOf(level1, level1.length);
						maze[1] = Arrays.copyOf(level2, level2.length);
						maze[2] = Arrays.copyOf(level3, level3.length);
						Sound.chompSound();
						remove(restartButton);
						buttonFlag2 = true;
					}
				});
				add(restartButton);
				buttonFlag2 = false;
			}
		}
	}

	private void drawMaze(Graphics g) {
		int i = 0;
		//engel,duvar,yem
		for (int y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) { 
			for (int x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE, i++) {
				if (maze[level][i] == 1) { // Horizontal Top Border
					g.setColor(Color.RED);
					g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE / 2);
				} else if (maze[level][i] == 16) { // Vertical pacmanLeft Border
					g.setColor(Color.RED);
					g.fillRect(x, y, BLOCK_SIZE / 2, BLOCK_SIZE);
				} else if (maze[level][i] == 21) { // Vertical pacmanRight Border
					g.setColor(Color.RED);
					g.fillRect(x + BLOCK_SIZE / 2, y, BLOCK_SIZE / 2, BLOCK_SIZE);
				} else if (maze[level][i] == 26) { // Corner Border
					g.setColor(Color.RED);
					g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
				} else if (maze[level][i] == 31) { // Horizontal Bottom Corner
					g.setColor(Color.RED);
					g.fillRect(x, y + BLOCK_SIZE / 2, BLOCK_SIZE, BLOCK_SIZE / 2);
				} else if (maze[level][i] == 0) { // Bait
					g.setColor(Color.WHITE);
					g.fillOval(x + (BLOCK_SIZE - 10) / 2, y + (BLOCK_SIZE - 10) / 2, 10, 10);
				} else if (maze[level][i] == 3) { // Big Bait
					g.setColor(Color.WHITE);
					g.fillOval(x + (BLOCK_SIZE - 20) / 2, y + (BLOCK_SIZE - 20) / 2, 20, 20);
				} else if (maze[level][i] == 6) { // Wall
					g.setColor(Color.GREEN);
					g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
				} else if (maze[level][i] == 11) { // Ghost Box
					g.setColor(Color.CYAN);
					g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
				}
			}
		}
	}

	private void movePacman(Graphics g) {
		int pos, ch;

		if (pacman.getX() % BLOCK_SIZE == 0 && pacman.getY() % BLOCK_SIZE == 0) { 
			pos = pacman.getX() / BLOCK_SIZE + N_BLOCKS * (int) (pacman.getY() / BLOCK_SIZE); 
			ch = maze[level][pos];

			if (ch == 0) {
				maze[level][pos] = 2;
				score++;
			} else if (ch == 3) {
				Sound.eatSound();
				maze[level][pos] = 2;
				score += 5;
				pacman.setSpeed(16);

				java.util.Timer timer2 = new java.util.Timer(); 
				timer2.schedule(new TimerTask() {
					@Override
					public void run() {
						pacman.setSpeed(8);
					}
				}, 5000);
			}

			pacman.setVelX(dx);
			pacman.setVelY(dy);
			
			//boþluklar
			if (pos % N_BLOCKS == N_BLOCKS - 1 && pacman.getVelX() == 1)
				pacman.setX(pacman.getX() - BLOCK_SIZE * N_BLOCKS);
			else if (pos % N_BLOCKS == 0 && pacman.getVelX() == -1)
				pacman.setX(pacman.getX() + BLOCK_SIZE * N_BLOCKS);
			else if (pos / N_BLOCKS == 0 && pacman.getVelY() == -1)
				pacman.setY(pacman.getY() + BLOCK_SIZE * N_BLOCKS);
			else if (pos / N_BLOCKS == N_BLOCKS - 1 && pacman.getVelY() == 1)
				pacman.setY(pacman.getY() - BLOCK_SIZE * N_BLOCKS);
			// collusion detection
			else if ((pacman.getVelX() == 1 && maze[level][pos + 1] % 5 == 1) 
					|| (pacman.getVelX() == -1 && maze[level][pos - 1] % 5 == 1)
					|| (pacman.getVelY() == 1 && maze[level][pos + N_BLOCKS] % 5 == 1)
					|| (pacman.getVelY() == -1 && maze[level][pos - N_BLOCKS] % 5 == 1)) {
				pacman.setVelX(0);
				pacman.setVelY(0);
			}
		}

		pacman.setX(pacman.getX() + pacman.getSpeed() * pacman.getVelX());
		pacman.setY(pacman.getY() + pacman.getSpeed() * pacman.getVelY());

		
		//update edilen konum ve gifler
		if (dx == -1 || pacman.getLastPosition() == 2)
			g.drawImage(pacmanLeft, pacman.getX() + (BLOCK_SIZE - (BLOCK_SIZE / 2)) / 2,
					pacman.getY() + (BLOCK_SIZE - (BLOCK_SIZE / 2)) / 2, BLOCK_SIZE / 2, BLOCK_SIZE / 2, this);
		else if (dx == 1 || pacman.getLastPosition() == 0)
			g.drawImage(pacmanRight, pacman.getX() + (BLOCK_SIZE - (BLOCK_SIZE / 2)) / 2,
					pacman.getY() + (BLOCK_SIZE - (BLOCK_SIZE / 2)) / 2, BLOCK_SIZE / 2, BLOCK_SIZE / 2, this);
		else if (dy == -1 || pacman.getLastPosition() == 3)
			g.drawImage(pacmanUp, pacman.getX() + (BLOCK_SIZE - (BLOCK_SIZE / 2)) / 2,
					pacman.getY() + (BLOCK_SIZE - (BLOCK_SIZE / 2)) / 2, BLOCK_SIZE / 2, BLOCK_SIZE / 2, this);
		else if (dy == 1 || pacman.getLastPosition() == 1)
			g.drawImage(pacmanDown, pacman.getX() + (BLOCK_SIZE - (BLOCK_SIZE / 2)) / 2,
					pacman.getY() + (BLOCK_SIZE - (BLOCK_SIZE / 2)) / 2, BLOCK_SIZE / 2, BLOCK_SIZE / 2, this);

		if (score == levelCheck && level != 2) {
			level++;
			N_GHOSTS++;
			continueGame();
			remainingTime = 90;
		} else if (score == levelCheck && level == 2) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(winImage, 0, 0, getWidth()-50, 200, this);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Tahoma", Font.BOLD, 40));		
			g.drawString("Your Score: " + score, 200, 500);
			N_GHOSTS=0;
			lives=0;
			remainingTime =0;
			
			
		}
	}

	private void moveGhosts(Graphics g) {
		int pos;
		
		//ghostlarýn kontrolü
		for (int i = 0; i < N_GHOSTS; i++) { 
			if (ghost[i].getX() % BLOCK_SIZE == 0 && ghost[i].getY() % BLOCK_SIZE == 0) {
				pos = ghost[i].getX() / BLOCK_SIZE + N_BLOCKS * (int) (ghost[i].getY() / BLOCK_SIZE);

				int randomInteger = random.nextInt(4);

				// Saða 
				if (randomInteger == 0) {
					if (maze[level][pos + 1] % 5 != 1)
						ghost[i].setVelX(1);
					else if (maze[level][pos - 1] % 5 != 1)
						ghost[i].setVelX(-1);
					else
						ghost[i].setVelX(0);

					ghost[i].setVelY(0);
				}
				// aþþaðý
				else if (randomInteger == 1) {
					if (maze[level][pos + N_BLOCKS] % 5 != 1) 
						ghost[i].setVelY(1);
					else if (maze[level][pos - N_BLOCKS] % 5 != 1)
						ghost[i].setVelY(-1);
					else
						ghost[i].setVelY(0);

					ghost[i].setVelX(0);
				}
				// sola 
				else if (randomInteger == 2) {
					if (maze[level][pos - 1] % 5 != 1)
						ghost[i].setVelX(-1);
					else if (maze[level][pos + 1] % 5 != 1)
						ghost[i].setVelX(1);
					else
						ghost[i].setVelX(0);

					ghost[i].setVelY(0);
				}
				// yukarý gitme
				else if (randomInteger == 3) {
					if (maze[level][pos - N_BLOCKS] % 5 != 1)
						ghost[i].setVelY(-1);
					else if (maze[level][pos + N_BLOCKS] % 5 != 1)
						ghost[i].setVelY(1);
					else
						ghost[i].setVelY(0);

					ghost[i].setVelX(0);
				}
			}

			ghost[i].setX(ghost[i].getX() + (ghost[i].getVelX() * ghost[i].getSpeed()));
			ghost[i].setY(ghost[i].getY() + (ghost[i].getVelY() * ghost[i].getSpeed()));

			g.drawImage(ghostImage, ghost[i].getX() + (BLOCK_SIZE - (BLOCK_SIZE / 2)) / 2,
					ghost[i].getY() + (BLOCK_SIZE - (BLOCK_SIZE / 2)) / 2, BLOCK_SIZE / 2, BLOCK_SIZE / 2, this);
			
			// hayaletin collesion detectioný 
			if (pacman.getX() > (ghost[i].getX() - (BLOCK_SIZE / 2)) 																		
					&& pacman.getX() < (ghost[i].getX() + (BLOCK_SIZE / 2))																			
					&& pacman.getY() > (ghost[i].getY() - (BLOCK_SIZE / 2))
					&& pacman.getY() < (ghost[i].getY() + (BLOCK_SIZE / 2)) && started) {
				lives--;
				Sound.deathSound();
				continueGame();
			}
		}
	}

	private void continueGame() {
		if (lives == 0) {
			gameover = true;
			Sound.stopChompSound();
			Sound.gameoverSound();
		}

		levelCheck = 0;
		for (int i = 0; i <= level; i++)
			levelCheck += levelScore[i];

		for (int i = 0; i < N_GHOSTS; i++) {
			ghost[i].setX(7 * BLOCK_SIZE);
			ghost[i].setY(7 * BLOCK_SIZE);
			ghost[i].setVelX(0);
			ghost[i].setVelY(0);
		}

		pacman.setX(7 * BLOCK_SIZE);
		pacman.setY(9 * BLOCK_SIZE);
		pacman.setVelX(0);
		pacman.setVelY(0);
		pacman.setSpeed(8);
		dx = dy = 0;
	}

	private void startCountdown() {
		timerFlag = false;

		java.util.Timer countdownTimer = new java.util.Timer();
		countdownTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				remainingTime -= 1;

				if (remainingTime == 0) {
					remainingTime = 60;
					lives--;
					continueGame();
				}
			}
		}, 1000, 1000);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint(); 
	}
}