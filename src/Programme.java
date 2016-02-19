import java.util.List;
import java.util.Map;

public class Programme {

	public static void main(String[] args) {
		Menu menu = new Menu();
		
		for(Course c : menu.getCourses()) {
			System.out.println(c.getCourseTypeName() + " " + c.getName());
			System.out.println();
		}
		
		Map<Class<? extends Course>, List<Course>> groups = menu.groupByCourseType();
		
		for (Map.Entry<Class<? extends Course>, List<Course>> entry : groups.entrySet()) {
		    Class<? extends Course> key = entry.getKey();
		    List<Course> value = entry.getValue();
		    System.out.print(Course.getCourseTypeNameOfClassCourseType(key) + " => ");
		    for(Course row : value) {
		    	System.out.print(row.getName() + ", ");
		    }
		    System.out.println();
		    System.out.println();
		}
	}

}
