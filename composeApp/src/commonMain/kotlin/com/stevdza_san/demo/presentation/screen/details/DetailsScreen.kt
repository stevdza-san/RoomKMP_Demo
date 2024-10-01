package com.stevdza_san.demo.presentation.screen.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DetailsScreen(
    onEditClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel = koinViewModel<DetailsViewModel>()
    val selectedBook by viewModel.selectedBook
    val isFavorite by viewModel.isFavorite

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back arrow icon"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit icon"
                        )
                    }
                    IconButton(
                        onClick = { viewModel.setFavoriteBook() }
                    ) {
                        Icon(
                            modifier = Modifier
                                .alpha(if (isFavorite) 1f else 0.38f),
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star icon"
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.deleteBook()
                            onBackClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete icon"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 12.dp)
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
            ) {
                CoilImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(size = 12.dp))
                        .fillMaxWidth(),
                    imageModel = { selectedBook?.image },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.alpha(0.5f),
                text = selectedBook?.category ?: "",
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = selectedBook?.title ?: "",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = selectedBook?.summary ?: ""
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                modifier = Modifier.alpha(0.5f),
                text = selectedBook?.author ?: "",
            )
            Spacer(modifier = Modifier.height(12.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                selectedBook?.tags?.forEach { tag ->
                    SuggestionChip(
                        onClick = {},
                        label = { Text(tag) }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
        }
    }
}