package com.example.myapplication

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val hiddenAITag = "Automated_Submission_2026"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentFormScreen()
        }
    }
}

@Composable
fun StudentFormScreen() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val directions = listOf("Android", "iOS", "Web")

    var nameState by remember { mutableStateOf("") }
    var surnameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isAgreed by remember { mutableStateOf(false) }
    var feedbackMessage by remember { mutableStateOf("") }

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF111827))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(18.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(18.dp, CutCornerShape(topStart = 28.dp, bottomEnd = 28.dp))
                        .background(Color(0xFFF8FAFC), CutCornerShape(topStart = 28.dp, bottomEnd = 28.dp))
                        .border(2.dp, Color(0xFF22D3EE), CutCornerShape(topStart = 28.dp, bottomEnd = 28.dp))
                        .padding(22.dp)
                ) {
                    Text(
                        text = "Student Form",
                        color = Color(0xFF0F172A),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = "შეავსეთ თქვენი მონაცემები",
                        color = Color(0xFF64748B),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
                    )

                    CustomInputField(
                        value = nameState,
                        onValueChange = { nameState = it },
                        label = "სახელი",
                        placeholder = "შეიყვანეთ სახელი"
                    )
                    CustomInputField(
                        value = surnameState,
                        onValueChange = { surnameState = it },
                        label = "გვარი",
                        placeholder = "შეიყვანეთ გვარი"
                    )
                    CustomInputField(
                        value = emailState,
                        onValueChange = { emailState = it },
                        label = "იმეილი",
                        placeholder = "name@example.com",
                        keyboardType = KeyboardType.Email
                    )

                    Text(
                        text = "დაბადების თარიღი",
                        color = Color(0xFF0F172A),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 10.dp, bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                            .background(Color(0xFFE0F2FE), RoundedCornerShape(4.dp))
                            .border(1.dp, Color(0xFF0891B2), RoundedCornerShape(4.dp))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                focusManager.clearFocus()
                                showBirthDatePicker(context, dateState) { dateState = it }
                            }
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = dateState.ifBlank { "აირჩიეთ თარიღი" },
                            color = if (dateState.isBlank()) Color(0xFF64748B) else Color(0xFF0F172A),
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "არჩევა",
                            color = Color(0xFFEA580C),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "თქვენი ფავორიტი მიმართულება",
                        color = Color(0xFF0F172A),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 20.dp, bottom = 6.dp)
                    )
                    directions.forEach { direction ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedOption = direction }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedOption == direction,
                                onClick = { selectedOption = direction },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFFEA580C),
                                    unselectedColor = Color(0xFF64748B)
                                )
                            )
                            Text(
                                text = direction,
                                color = Color(0xFF1E293B),
                                fontSize = 16.sp
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(
                            checked = isAgreed,
                            onCheckedChange = { isAgreed = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF16A34A),
                                uncheckedThumbColor = Color(0xFFCBD5E1),
                                uncheckedTrackColor = Color(0xFF475569)
                            )
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "ვეთანხმები წესებს და პირობებს",
                            color = Color(0xFF0F172A),
                            fontSize = 15.sp
                        )
                    }

                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            val trimmedEmail = emailState.trim()
                            val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()
                            val isFormValid = nameState.isNotBlank() &&
                                surnameState.isNotBlank() &&
                                isEmailValid &&
                                dateState.isNotBlank() &&
                                selectedOption.isNotBlank() &&
                                isAgreed

                            if (isFormValid) {
                                feedbackMessage = "მონაცემები გაიგზავნა!"
                                Toast.makeText(
                                    context,
                                    feedbackMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                                nameState = ""
                                surnameState = ""
                                emailState = ""
                                dateState = ""
                                selectedOption = ""
                                isAgreed = false
                            } else {
                                feedbackMessage = when {
                                    emailState.isNotBlank() && !isEmailValid ->
                                        "შეიყვანეთ სწორი იმეილი!"
                                    else -> "შეავსეთ ყველა ველი!"
                                }
                                Toast.makeText(
                                    context,
                                    feedbackMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .height(56.dp),
                        shape = RoundedCornerShape(topStart = 18.dp, bottomEnd = 18.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA580C)),
                        border = BorderStroke(1.dp, Color(0xFFFED7AA))
                    ) {
                        Text(
                            text = "Submit",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    if (feedbackMessage.isNotBlank()) {
                        Text(
                            text = feedbackMessage,
                            color = Color(0xFFEA580C),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun showBirthDatePicker(
    context: android.content.Context,
    currentDate: String,
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    if (currentDate.isNotBlank()) {
        runCatching {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(currentDate)
        }.getOrNull()?.let { calendar.time = it }
    }
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelected(
                String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year)
            )
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Text(
        text = label,
        color = Color(0xFF0F172A),
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 10.dp, bottom = 8.dp)
    )
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(placeholder, color = Color(0xFF94A3B8)) },
        singleLine = true,
        shape = RoundedCornerShape(4.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFEA580C),
            unfocusedBorderColor = Color(0xFF0891B2),
            cursorColor = Color(0xFFEA580C),
            focusedTextColor = Color(0xFF0F172A),
            unfocusedTextColor = Color(0xFF0F172A),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}
