package br.ce.wcaquino.taskbackend.controller;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Date;

public class TaskControllerTest {

    @Mock
    private TaskRepo repo;

    @InjectMocks
    private TaskController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldNotCreateTaskWithoutDescription() {
        Task task = new Task();
        task.setDueDate(LocalDate.now());
        try {
            controller.save(task);
            Assert.fail("não deve chegar aqui");
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the task description", e.getMessage());
        }
    }

    @Test
    public void shouldNotCreateTaskWithoutDate() {
        Task task = new Task();
        task.setTask("Teste");
        //task.setDueDate(LocalDate.now());
        try {
            controller.save(task);
//            Assert.fail("should not get here");
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the due date", e.getMessage());
        }
    }

    @Test
    public void shouldNotCreateTaskWithPastDate() {
        Task task = new Task();
        task.setTask("Teste");
        task.setDueDate(LocalDate.of(2010, 01, 01));
        //task.setDueDate(LocalDate.now());
        try {
            controller.save(task);
//            Assert.fail("should not get here");
        } catch (ValidationException e) {
            Assert.assertEquals("Due date must not be in past", e.getMessage());
        }
    }

    @Test
    public void shouldCreateTaskSuccessfully() throws ValidationException {
        Task task = new Task();
        //scenary
        task.setTask("Teste");
        task.setDueDate(LocalDate.now());
        //task.setDueDate(LocalDate.now());

        controller.save(task);

        //validation
        Mockito.verify(repo).save(task);

    }

}
