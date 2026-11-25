package com.example.profildeveloppeur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profildeveloppeur.ui.theme.ProfilDeveloppeurTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfilDeveloppeurTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // innerPadding : espace laissé par le Scaffold (barres système)
                    ProfileScreen(
                        name = "Adem Fellah",
                        role = "Développeur Android junior",
                        description = "Étudiant en informatique passionné par le mobile. " +
                            "Je crée des applications Android avec Kotlin et Jetpack Compose.",
                        skills = listOf("Kotlin", "Jetpack Compose", "Git / GitHub", "Android Studio"),
                        contact = "adem.fellah@email.com",
                        availability = "Disponible pour une alternance",
                        onContactClick = { contacter() },
                        onAvailabilityClick = { afficherDisponibilite() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun contacter() {
        // Fonction vide : ici on pourrait ouvrir l'application mail.
    }

    private fun afficherDisponibilite() {
        // Fonction vide : ici on pourrait afficher le détail de la disponibilité.
    }
}

/**
 * 1. Écran principal : place la fiche de profil dans l'écran.
 */
@Composable
fun ProfileScreen(
    name: String,
    role: String,
    description: String,
    skills: List<String>,
    contact: String,
    availability: String,
    onContactClick: () -> Unit,
    onAvailabilityClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        ProfileCard(
            name = name,
            role = role,
            description = description,
            skills = skills,
            contact = contact,
            availability = availability,
            onContactClick = onContactClick,
            onAvailabilityClick = onAvailabilityClick,
            // PADDING EXTERNE : c'est le parent (l'écran) qui décide
            // de laisser 16.dp autour de la fiche.
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

/**
 * 2. Fiche de profil : regroupe tous les éléments du profil.
 */
@Composable
fun ProfileCard(
    name: String,
    role: String,
    description: String,
    skills: List<String>,
    contact: String,
    availability: String,
    onContactClick: () -> Unit,
    onAvailabilityClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        // PADDING INTERNE : la fiche espace son propre contenu de ses bords.
        Column(modifier = Modifier.padding(20.dp)) {
            ProfileHeader(name = name, role = role)

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 12.dp)
            )

            SkillList(
                skills = skills,
                modifier = Modifier.padding(top = 16.dp)
            )

            InfoLine(
                label = "Contact",
                value = contact,
                modifier = Modifier.padding(top = 16.dp)
            )

            AvailabilityBadge(
                text = availability,
                onClick = onAvailabilityClick,
                modifier = Modifier.padding(top = 16.dp)
            )

            ContactButton(
                text = "Contacter",
                onClick = onContactClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}

/**
 * 3. En-tête du profil : nom et rôle.
 */
@Composable
fun ProfileHeader(
    name: String,
    role: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = role,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

/**
 * 4. Liste des compétences : réutilise SkillItem pour chaque compétence
 * (pas de copier-coller du même bloc).
 */
@Composable
fun SkillList(
    skills: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Compétences",
            style = MaterialTheme.typography.titleSmall
        )
        for (skill in skills) {
            SkillItem(
                skill = skill,
                // padding externe : espace entre deux compétences
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}

/**
 * 5. Une compétence.
 */
@Composable
fun SkillItem(
    skill: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        // padding interne : espace entre le texte et le bord de l'étiquette
        Text(
            text = skill,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

/**
 * 6. Information secondaire : un libellé et une valeur (ex. le contact).
 */
@Composable
fun InfoLine(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

/**
 * 7. Zone cliquable personnalisée pour la disponibilité.
 * Elle signale le clic via le callback onClick, sans logique applicative.
 */
@Composable
fun AvailabilityBadge(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        // CHAÎNE DE MODIFIERS : largeur -> clic (rôle bouton) -> description lecteur d'écran
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClickLabel = "Voir les détails de la disponibilité",
                role = Role.Button,
                onClick = onClick
            )
            .semantics {
                contentDescription = "$text. Bouton : voir les détails de la disponibilité"
            },
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.tertiaryContainer
    ) {
        Text(
            text = "✅ $text",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * 8. Bouton d'action. Un Button est déjà reconnu comme bouton par l'accessibilité.
 */
@Composable
fun ContactButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = text)
    }
}

// ---------- PREVIEWS : deux variantes du profil ----------

@Preview(showBackground = true, name = "Profil Android junior")
@Composable
fun ProfileAndroidJuniorPreview() {
    ProfilDeveloppeurTheme {
        ProfileScreen(
            name = "Adem Fellah",
            role = "Développeur Android junior",
            description = "Étudiant en informatique passionné par le mobile. " +
                "Je crée des applications Android avec Kotlin et Jetpack Compose.",
            skills = listOf("Kotlin", "Jetpack Compose", "Git / GitHub", "Android Studio"),
            contact = "adem.fellah@email.com",
            availability = "Disponible pour une alternance",
            onContactClick = {},
            onAvailabilityClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Profil fullstack")
@Composable
fun ProfileFullstackPreview() {
    ProfilDeveloppeurTheme {
        ProfileScreen(
            name = "Ines Damir",
            role = "Développeuse fullstack freelance",
            description = "Je conçois des applications web de bout en bout, " +
                "de l'interface jusqu'à la base de données.",
            skills = listOf("TypeScript", "React", "Node.js", "PostgreSQL", "Docker"),
            contact = "ines.damir@email.com",
            availability = "Disponible en freelance dès janvier",
            onContactClick = {},
            onAvailabilityClick = {}
        )
    }
}
