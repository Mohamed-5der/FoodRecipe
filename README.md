# Recipe Food Application

## Overview

This Recipe Application allows users to explore, save, and view detailed recipes, including recipe-related videos. The app uses Firebase Authentication for secure login and registration, supporting both email/password authentication and Google Sign-In. It also uses Room database for storing users' favorite recipes locally. Built with MVVM architecture, Dagger Hilt for dependency injection, Retrofit for network requests, Jetpack Compose for UI, and Voyager for navigation, the app provides a seamless and efficient user experience.

## Features

- **Splash Screen**: 
  - Displays a splash screen that checks the user's login status using Shared Preferences.
  - If logged in, navigates to the Home Fragment.
  - If not logged in, shows the Login/Register screens.

- **Authentication (Login & Register)**:
  - **Email/Password Authentication**: Secure user login and registration using Firebase Authentication.
  - **Google Sign-In**: Allows users to sign in using their Google account for a more convenient login experience.
  - User session is securely managed through Firebase.

- **Home Fragment**:
  - Displays a list of recipes fetched from the MealDB API.
  - Allows users to click on a recipe to view detailed information.

- **Recipe Detail Fragment**:
  - Shows the recipe’s image, title, and brief details.
  - Allows saving recipes to the favorites list.
  - Includes a video button to watch a YouTube video related to the recipe.

- **Favorite Fragment**:
  - Uses Room database to store and display a list of the user’s favorite recipes.
  - Allows users to remove recipes from their favorites.

- **Search Fragment**:
  - Provides search functionality for finding recipes by name or keyword.
  - Displays search results with the ability to navigate to recipe details.

- **Profile & Settings**:
  - Profile management options (e.g., update user information).
  - App settings to adjust preferences.

- **YouTube Video Integration**:
  - Embedded YouTube video for recipes with video instructions.

- **Bottom Navigation**:
  - Allows easy navigation between Home, Favorites, Search, and Profile sections using Voyager navigation.

- **Option Menu**:
  - Includes "Sign Out" and "About the Creator" options.

- **About Fragment**:
  - Displays information about the app’s creator and a synopsis of the application.

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (for storing favorite recipes locally)
- **Authentication**: 
  - **Firebase Authentication**: For user login and registration.
  - **Google Sign-In**: For a convenient sign-in option.
- **Dependency Injection**: Dagger Hilt
- **Networking**: Retrofit (for fetching recipes from the MealDB API)
- **Navigation**: Voyager
- **Session Management**: Shared Preferences (for managing user login sessions)
- **YouTube API Integration**: Embedded video player using YouTube links

## Setup and Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/Mohamed-5der/FoodRecipe.git
