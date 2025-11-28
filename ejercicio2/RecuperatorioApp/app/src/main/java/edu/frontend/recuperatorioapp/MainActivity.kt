package edu.frontend.recuperatorioapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.frontend.recuperatorioapp.ui.theme.RecuperatorioAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecuperatorioAppTheme {
                PerfilUsuarioScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuarioScreen(
    onEditClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Perfil de Usuario",
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )
                )
            }
        },
        containerColor = Color(0xFFFAFAFA)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            // --- AVATAR Y NOMBRE ---
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF666666)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Foto de perfil",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Ana García",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(text = "Mobile Developer", fontSize = 20.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(180.dp))
        }
    }
