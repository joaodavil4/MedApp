@file:OptIn(ExperimentalMaterial3Api::class)

package com.leafwise.medapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.leafwise.medapp.R
import com.leafwise.medapp.domain.model.TypeMedication
import com.leafwise.medapp.presentation.extensions.getCurrentLocale
import com.leafwise.medapp.util.extensions.toHourFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun SelectorItem (
    modifier: Modifier = Modifier,
    label: String,
    options: Array<out Any?>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            label = { Text(label) },
            value = options[selectedIndex].toString(),
            onValueChange = {},
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {

            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(option.toString()) },
                    onClick = {
                        onSelect(index)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun TextItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onChange: (String) -> Unit,
){
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = true
    )
}

@Composable
fun SelectDateItem(
    modifier: Modifier = Modifier,
    label: String,
    value: Calendar,
    onValueChange: (String) -> Unit,
) {

    val locale = LocalContext.current.getCurrentLocale()

    val datePickerDialog = remember { mutableStateOf(false) }
    val timePicker = remember { mutableStateOf(value) }


    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                datePickerDialog.value = it.isFocused
            },
        value = timePicker.value.toHourFormat(locale),
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(text = label) },
        readOnly = true,
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_hour_picker),
                contentDescription = "Hour icon",
                modifier = Modifier.clickable {
                    datePickerDialog.value = true
                }
            )
        },
    )

    if (datePickerDialog.value)
        TimePickerDialog(
            onDismissRequest = { datePickerDialog.value = false },
            onTimeChanged = {
                timePicker.value = it
            },
            selectedTime = timePicker.value,
            confirmLabel = stringResource(id = R.string.select_date_confirm_label),
            title = stringResource(id = R.string.select_date_time_picker_title)
        )

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeChanged: (Calendar) -> Unit,
    selectedTime: Calendar,
    confirmLabel: String,
    title: String,
) {
    val timePickerState = rememberTimePickerState(
        selectedTime.get(Calendar.HOUR_OF_DAY),
        selectedTime.get(Calendar.MINUTE)
    )

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = {
                val timeSelection = Calendar.getInstance()
                timeSelection.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                timeSelection.set(Calendar.MINUTE, timePickerState.minute)
                onTimeChanged(timeSelection)
                onDismissRequest()
            }) {
                Text(text = confirmLabel)
            }
        },
        title = { Text(text = title) },
        text = {
            TimePicker(state = timePickerState)
        },
    )
}

@Preview(showBackground = true)
@Composable
fun SelectorItemPreview(){
    SelectorItem(
        label = "Quantity",
        options = arrayOf("1", "2", "3", "4"),
        selectedIndex = 0,
        onSelect = {}
    )
}

@Preview(showBackground = true)
@Composable
fun TextItemPreview(){
    TextItem(
        label = "Text",
        onChange = {},
        value = "Hello World"
    )
}

@Preview(
    showBackground = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun SelectDateItemPreview(){
    SelectDateItem(
        label = "Dose 1",
        value = Calendar.getInstance(),
        onValueChange = {}
    )
}