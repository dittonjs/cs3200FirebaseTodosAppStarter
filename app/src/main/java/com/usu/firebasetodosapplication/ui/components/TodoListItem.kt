package com.usu.firebasetodosapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.usu.firebasetodosapplication.ui.models.Todo
import com.usu.firebasetodosapplication.ui.theme.FirebaseTodosApplicationTheme

@Composable
fun TodoListItem(
    todo: Todo,
    toggle: (checked: Boolean) -> Unit = {},
    onEditPressed: () -> Unit = {}
) {
    Surface(
        elevation = 2.dp,
        shape = RoundedCornerShape(4.dp),
    ) {
        Column (){
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = todo.completed == true, onCheckedChange = toggle)
                    Text(text = todo.title ?: "", style = MaterialTheme.typography.subtitle2)
                }
                IconButton(onClick = onEditPressed) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit button")
                }
            }
            Row(modifier = Modifier.padding(16.dp, 0.dp)) {
                Text(text = todo.description ?: "")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = when(todo.priority) {
                    Todo.PRIORITY_HIGH -> "Priority: HIGH"
                    Todo.PRIORITY_MEDIUM -> "Priority: MEDIUM"
                    Todo.PRIORITY_LOW -> "Priority: LOW"
                    else -> ""
                },
                modifier = Modifier.padding(16.dp, 0.dp)
            )

            Text(
                text = "${todo.estimatedCompletionTime ?: ""}hrs",
                modifier = Modifier.padding(16.dp, 0.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
fun TodoListItemPreview() {
    FirebaseTodosApplicationTheme {
        TodoListItem(todo = Todo(
            title = "Go pick up grandma",
            description = "Grandma needs to go to the store at 5:15",
            estimatedCompletionTime = 5,
            id = "fcs3k3lkm2309mgfs",
            priority = Todo.PRIORITY_LOW,
            completed = true,
            userId = "23oiknmf09sj0982jlkjsdf"
        ))
    }
}