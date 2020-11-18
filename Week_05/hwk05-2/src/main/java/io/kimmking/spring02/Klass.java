package io.kimmking.spring02;

import io.kimmking.spring01.Student;
import lombok.Data;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

@Data
public class Klass { 
    
	@Resource(name="students")
    List<Student> students;
    
    public void dong(){
        System.out.println(this.getStudents());
    }
    
}
