package main;

import main.model.Task;
import main.model.TaskRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping(value = "/tasks", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> task(@RequestBody Task requestTask){

        requestTask.setCreationTime(new Date());
        requestTask.setDone(false);

        taskRepository.save(requestTask);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity getById(@PathVariable int id) {

        Optional<Task> optionalTask = taskRepository.findById(id);

        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(optionalTask.get());
    }

    @GetMapping("/tasks")
    public ResponseEntity getAll(){

        Iterable<Task> taskIterable = taskRepository.findAll();
        ArrayList<Task> tasks= new ArrayList<>();
        for(Task task : taskIterable) {
            tasks.add(task);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity update(@PathVariable int id, @RequestBody String requestTask){

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Task repositoryTask = optionalTask.get();

        JSONObject jsonRequestTask = new JSONObject(requestTask);

        if(!jsonRequestTask.isNull("isDone")){
            repositoryTask.setDone(jsonRequestTask.getBoolean("isDone"));

        }
        if(!jsonRequestTask.isNull("title")){
            repositoryTask.setTitle(jsonRequestTask.getString("title"));
        }

        if(!jsonRequestTask.isNull("description")){
            repositoryTask.setDescription(jsonRequestTask.getString("description"));
        }

        taskRepository.save(repositoryTask);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("tasks/{id}")
    public ResponseEntity delete(@PathVariable int id){

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        taskRepository.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
