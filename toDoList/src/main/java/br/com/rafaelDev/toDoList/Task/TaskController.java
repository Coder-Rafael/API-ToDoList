package br.com.rafaelDev.toDoList.Task;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/registerTask")
    public TaskModel registerTask(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var idUser = request.getAttribute("idUsuario");
         System.out.println("/registrar"+idUser);
        taskModel.setIdUsuario((UUID) idUser);
        var createdTask = this.taskRepository.save(taskModel);
        return createdTask;
    }

    @GetMapping("/listarTasks")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUsuario");
        System.out.println("/listar"+idUser);
        var tasks = this.taskRepository.findByIdUsuario((UUID)idUser);
        System.out.println(tasks);
        return tasks;
    }
}
