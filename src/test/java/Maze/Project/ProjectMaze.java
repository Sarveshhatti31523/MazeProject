package Maze.Project;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProjectMaze {

	static int a = 0;
	int b = 0;
	static boolean finish = false;
	int i = 0;
	static ArrayList<String> last_visited = new ArrayList<String>();
	static ArrayList<String> reversePath = new ArrayList<String>();
	 static ArrayList<Boolean> Maze = new ArrayList<Boolean>();
	static int wrongRight;
	static int wrongLeft;
	static int wrongUp;
	static int wrongDown;
	static boolean deadEnd;

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\sarvesh.hatti\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://127.0.0.1:8080/");
		// driver.manage().window().maximize();

		driver.findElement(By.xpath("//*[text()='Right']")).click();
		last_visited.add("Left");
//		while(a<50) {
//		ProjectMaze.available_paths(driver);
//		a++;
//		}

		int loopCounter = 1;
		do {
			deadEnd = false;
			int c = available_paths(driver);
			if (c > 2) {
				// it is a junction
				reversePath.clear();
				do {
					//c = available_paths(driver);

					if (c == 2) {
						// Not a Junction, Just the regular path
						keepTrackAndGoInAvailablePaths(driver, c);
					}

					else if (c > 2 && loopCounter == 501) {
						// This loop is used to terminate the current junction and start over
						loopCounter = 201;
						break;
					}

					else if (c > 2) {
						// Junstion inside a junction found
						keepTrackAndGoInAvailablePaths(driver, c);
						loopCounter = 501;
					}

					else if (c == 1) {
						// Reached the dead end
						DeadEnd(driver);
						loopCounter = 201;
					}

				} while (deadEnd == false);
			} else if (c == 2) {
				// it is not a junction
				keepTrackAndGoInAvailablePaths(driver, c);
			} else if (c == 1) {
				// finish point
				finish = true;
			}
		} while (finish == false);
	}

	public static int available_paths(WebDriver driver) {

//		  ArrayList<Boolean> Maze;
		String str = driver.findElement(By.xpath("//div[contains(@class,'currentCell')]")).getAttribute("class");
		System.out.println(str);
		String col = str.substring(11, 13);
		String row = str.substring(14, 16);
		System.out.println(row + "," + col);

		String[] array_row = new String[10];
		String[] array_col = new String[10];
		array_row = row.split("");
		array_col = col.split("");
		int col_num = Integer.parseInt(array_col[1]);
		int row_num = Integer.parseInt(array_row[1]);
		System.out.println(array_row[0] + "," + array_row[1] + "," + array_col[0] + "," + array_col[1]);

		boolean path = false;
		String Right = ".c" + (col_num + 1) + "." + row;
		String Left = ".c" + +(col_num - 1) + "." + row;
		String Up = "." + col + ".r" + (row_num - 1);
		String Down = "." + col + ".r" + (row_num + 1);

		String Right_path = driver.findElement(By.cssSelector(Right)).getAttribute("class");
		String Left_path = driver.findElement(By.cssSelector(Left)).getAttribute("class");
		String Up_path = driver.findElement(By.cssSelector(Up)).getAttribute("class");
		String Down_path = driver.findElement(By.cssSelector(Down)).getAttribute("class");

		String[] Right_split = Right_path.split(" ");
		String[] Left_split = Left_path.split(" ");
		String[] Up_split = Up_path.split(" ");
		String[] Down_split = Down_path.split(" ");

		boolean Right_way = false;
		boolean Left_way = false;
		boolean Up_way = false;
		boolean Down_way = false;

		if (!(Right_path.contains("inactive"))) {

			Right_way = true;
		}

		if (!(Left_path.contains("inactive"))) {

			Left_way = true;
		}

		if (!(Up_path.contains("inactive"))) {

			Up_way = true;
		}

		if (!(Down_path.contains("inactive"))) {

			Down_way = true;
		}

//		ArrayList<Boolean> Maze = new ArrayList<Boolean>();

		Maze.add(Right_way);
		Maze.add(Left_way);
		Maze.add(Up_way);
		Maze.add(Down_way);
		// int c=Maze.size();

		int count = 0;
		for (int x = 0; x <= (Maze.size()) - 1; x++) {
			if (Maze.get(x) == true) {
				++count;
			}
		}

//		if (count == 2)
//		{
//			//this is regular path
//			if ((Maze.get(0) == true) && !(last_visited.get(last_visited.size()-1)=="Right") ) {
//
//				driver.findElement(By.xpath("//*[text()='Right']")).click();
//				last_visited.add("Left");
//				
//			}
//			else if (Maze.get(1) == true && !(last_visited.get(last_visited.size()-1)=="Left")) {
//				driver.findElement(By.xpath("//*[text()='Left']")).click();
//				last_visited.add("Right");
//			}
//			else if (Maze.get(2) == true && !(last_visited.get(last_visited.size()-1)=="Up")) {
//				driver.findElement(By.xpath("//*[text()='Up']")).click();
//				last_visited.add("Down");
//			}
//
//			else if (Maze.get(3) == true && !(last_visited.get(last_visited.size()-1)=="Down")) {
//				driver.findElement(By.xpath("//*[text()='Down']")).click();
//				last_visited.add("Up");
//			}
//		}
//		else if (count >2)
//		{
//			ArrayList<String> reversePath=new ArrayList<String>();
//			//check for available directions
//			for(int j=1;j<=count;j++) {
//				int lbcLastIndex = last_visited.size() - 1;
//                if (Maze.get(3) == true && (last_visited.get(lbcLastIndex) != "Down") && (wrongUp++ != 401))
//                {
//
//                	driver.findElement(By.xpath("//*[text()='Down']")).click();
//                    reversePath.add("Up");
//                    break;
//                }
//                else if (((Maze.get(0) == true) && (last_visited.get(lbcLastIndex) != "Right") )&& (wrongLeft++ != 401))
//                {
//                	driver.findElement(By.xpath("//*[text()='Right']")).click();
//                    reversePath.add("Left");
//                    break;
//                }
//                else if (Maze.get(1) == true && (last_visited.get(lbcLastIndex) != "Left") && (wrongRight++ != 401))
//                {
//                	driver.findElement(By.xpath("//*[text()='Left']")).click();
//                    reversePath.add("Right");
//                    break;
//                }
//                else if (Maze.get(2) == true && (last_visited.get(lbcLastIndex) != "Up") && (wrongDown++ != 401))
//                {
//                	driver.findElement(By.xpath("//*[text()='Up']")).click();
//                    reversePath.add("Down");
//                    break;
//                }
//
//               
//			}
//		}
//			//this is a junction
//			
//		
//		else if (count ==1)
//		{
//			finish = true;
//			break;
//			//Dead end
//			//finish
//		}
//		}while(finish == true);

		//Maze.clear();
		return count;
	}

	public static void DeadEnd(WebDriver driver) {

		boolean deadEnd = true;
		// String [] reversePath=new String[20];
		if (deadEnd == true) {
			String wrongPath = reversePath.get(0);
			{
				if (wrongPath == "right_btn") {
					wrongRight = 401;
					last_visited.add("Left");
				} else if (wrongPath == "left_btn") {
					wrongLeft = 401;
					last_visited.add("Right");

				} else if (wrongPath == "up_btn") {
					wrongUp = 401;
					last_visited.add("Down");
				} else if (wrongPath == "down_btn") {
					wrongDown = 401;
					last_visited.add("Up");
				}
			}
			for (int i1 = (reversePath.size() - 1); i1 >= 0; i1--) {
				if (reversePath.get(i1) == "right_btn") {
					driver.findElement(By.xpath("//*[text()='Right']")).click();
				} else if (reversePath.get(i1) == "left_btn") {
					driver.findElement(By.xpath("//*[text()='Left']")).click();
				} else if (reversePath.get(i1) == "down_btn") {
					driver.findElement(By.xpath("//*[text()='Down']")).click();
				} else if (reversePath.get(i1) == "up_btn") {
					driver.findElement(By.xpath("//*[text()='Up']")).click();
				}
			}
		}
		reversePath.clear();
	}

	public static void keepTrackAndGoInAvailablePaths(WebDriver driver, int count) {
		
		for (int j = 1; j <= count; j++) {
			int lbcLastIndex = last_visited.size() - 1;
			System.out.println(Maze.size()+","+lbcLastIndex);
			if ((Maze.get(3) == true) && (last_visited.get(lbcLastIndex) != "Down") && (wrongUp++ != 401)) {

				driver.findElement(By.xpath("//*[text()='Down']")).click();
				last_visited.add("Up");
				reversePath.add("Up");
				Maze.clear();
				break;
			} else if ((Maze.get(0) == true) && (last_visited.get(lbcLastIndex) != "Right") && (wrongLeft++ != 401)) {
				driver.findElement(By.xpath("//*[text()='Right']")).click();
				last_visited.add("Left");
				reversePath.add("Left");
				Maze.clear();
				break;
			} else if (Maze.get(1) == true && (last_visited.get(lbcLastIndex) != "Left") && (wrongRight++ != 401)) {
				driver.findElement(By.xpath("//*[text()='Left']")).click();
				last_visited.add("Right");
				reversePath.add("Right");
				Maze.clear();
				break;
			} else if (Maze.get(2) == true && (last_visited.get(lbcLastIndex) != "Up") && (wrongDown++ != 401)) {
				driver.findElement(By.xpath("//*[text()='Up']")).click();
				last_visited.add("Down");
				reversePath.add("Down");
				Maze.clear();
				break;
			}

		}
	}
}
