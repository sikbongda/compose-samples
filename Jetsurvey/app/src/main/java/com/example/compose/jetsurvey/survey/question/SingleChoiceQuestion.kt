/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.jetsurvey.survey.question

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.compose.jetsurvey.R
import com.example.compose.jetsurvey.survey.Answer
import com.example.compose.jetsurvey.survey.AnswerOption
import com.example.compose.jetsurvey.theme.JetsurveyTheme

/**
 * Shows a list of possible answers with image and text and a radio button.
 * @param options a list of all possible answers with their icon and text value
 * @param answer the chosen answer (identified by text resource value), can be null if no answer is
 * chosen
 * @param onAnswerSelected callback that is called when a possible answer is clicked
 * @param modifier Modifier to be applied to the [SingleChoiceQuestion]
 */
@Composable
fun SingleChoiceQuestion(
    options: List<AnswerOption>,
    answer: Answer.SingleChoice?,
    onAnswerSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.selectableGroup()) {
        options.forEach { option ->
            SingleChoiceAnswer(
                text = stringResource(option.textRes),
                painter = option.iconRes?.let { painterResource(it) },
                selected = option.textRes == answer?.answer,
                onOptionSelected = { onAnswerSelected(option.textRes) },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun SingleChoiceAnswer(
    text: String,
    painter: Painter?,
    selected: Boolean,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = if (selected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        },
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        ),
        modifier = modifier.selectable(selected) { onOptionSelected() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (painter != null) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(MaterialTheme.shapes.extraSmall)
                )
                Spacer(Modifier.width(8.dp))
            }
            Text(text, Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
            RadioButton(selected, onClick = null, Modifier.padding(8.dp))
        }
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SingleChoiceAnswerPreview(
    @PreviewParameter(SelectedProvider::class, limit = 2) selected: Boolean
) {
    JetsurveyTheme {
        SingleChoiceAnswer(
            text = "Preview",
            painter = painterResource(id = R.drawable.frag),
            selected = selected,
            onOptionSelected = { }
        )
    }
}

private class SelectedProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(false, true)
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SingleChoiceQuestionPreview() {
    var selectedAnswer: Answer.SingleChoice? by remember {
        mutableStateOf(Answer.SingleChoice(R.string.bugchaos))
    }

    JetsurveyTheme {
        SingleChoiceQuestion(
            options = listOf(
                AnswerOption(R.string.spark, R.drawable.spark),
                AnswerOption(R.string.lenz, R.drawable.lenz),
                AnswerOption(R.string.bugchaos, R.drawable.bug_of_chaos),
                AnswerOption(R.string.frag, R.drawable.frag)
            ),
            answer = selectedAnswer,
            onAnswerSelected = { textRes -> selectedAnswer = Answer.SingleChoice(textRes) }
        )
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SingleChoiceQuestionPreview2() {
    var selectedAnswer: Answer.SingleChoice? by remember {
        mutableStateOf(Answer.SingleChoice(R.string.bugchaos))
    }

    JetsurveyTheme {
        SingleChoiceQuestion(
            options = listOf(
                AnswerOption(R.string.star_trek),
                AnswerOption(R.string.social_network),
                AnswerOption(R.string.back_to_future),
                AnswerOption(R.string.outbreak)
            ),
            answer = selectedAnswer,
            onAnswerSelected = { textRes -> selectedAnswer = Answer.SingleChoice(textRes) }
        )
    }
}
