# Shopping List Application

This project is an Android Shopping List application developed as part of an assignment for the AIT Mobile Software Development course. The application is built using Jetpack Compose, Room persistence, and follows modern Android development best practices.

## Features

- **Splash Screen**: Displays a custom logo for 3 seconds before navigating to the main Shopping List screen.

- **Shopping List Display**: The main Shopping List screen is implemented using a `LazyColumn`. Each item in the list includes:
  - Icon based on the item's category (e.g., Food, Electronics, Books, etc.).
  - Name of the item.
  - Checkbox to mark if the item has been bought (status can be updated during shopping).

- **Item Attributes**: Each item includes:
  - **Category**: Food, Electronics, Book, etc.
  - **Name**: Name of the item.
  - **Description**: Brief description of the item.
  - **Estimated Price**: Price value estimation.
  - **Status**: Boolean indicating if the item has been bought.

- **New Item Dialog**: A "New Item" button (implemented as a Floating Action Button) allows users to add new items. The dialog validates all required fields.

- **Edit and Delete Items**:
  - **Edit**: Users can edit an existing item.
  - **Delete**: Users can remove items one-by-one or use a "Delete All" button to clear the entire list.

- **Persistence**: Room database is used to persist data, ensuring that all items are retained even after closing the application.

- **Error Handling**: Proper error messages for empty fields and invalid inputs are provided in the New Item dialog.

- **Extra Feature**: A "View Item Details" button is included for each item, displaying all attributes of the selected item on a new screen for better detail viewing.

- **Application Name and Icon**: Customized in the Manifest to properly reflect the application's purpose.

## Technical Details

- **Jetpack Compose**: Used for building the UI, including `LazyColumn` for displaying the shopping items and Dialogs for adding/editing items.
- **Navigation Component**: Utilizes `NavHost` to navigate between the splash screen, main shopping list, and item detail views.
- **State Management**: State hoisting, `ViewModel`, and `remember` functions are employed to effectively manage UI state.
- **Persistence Storage**: Room persistence library is used for storing shopping items with DAO classes utilizing Flow and suspend functions for seamless interaction.

## Project Architecture

- **SplashScreen Composable**: Shows the app logo for 3 seconds.
- **ShoppingListScreen Composable**: Displays the list of shopping items with functionality to add, edit, and delete items.
- **Navigation**: Handled using `NavHost` to navigate between different screens in the app.
- **ShoppingViewModel**: Responsible for managing business logic related to the shopping list, interacting with the Room database for CRUD operations.
- **Room Database**: Used to store shopping items persistently.

## Packages and File Structure

The code is organized in a structured manner to follow best practices:
- **ViewModels**: Contains `ShoppingViewModel` for business logic.
- **UI Components**: Includes Composables like `ShoppingListScreen`, `SplashScreen`, `ItemDetailScreen`, etc.
- **Data Layer**: Includes the Room `Database`, `DAO`, and `Entities` for item storage.

## Sample Screenshots

Here are some screenshots of the Shopping List Application:

- **Splash Screen**: 
  ![Splash Screen](images/splash_screen.png)

- **Shopping List Screen**: 
  ![Shopping List](images/shopping_list_screen.png)

- **New Item Dialog**: 
  ![New Item Dialog](images/new_item_dialog.png)
  
- **Edit Item Dialog**: 
  ![Edit Item](images/edit_item_dialog.png)

- **Categories List Screen**: 
  ![Categories List](images/categories_list.png)

- **Summary Screen**: 
  ![Summary](images/summary_screen.png)


  

## How to Run the Project

1. Clone the repository:
   ```bash
   git clone [repository_url]
   ```
2. Open the project in Android Studio.
3. Build the project and run it on an Android emulator or physical device.

## Libraries Used

- **Jetpack Compose**: For building declarative UI.
- **Navigation Component**: For managing app navigation.
- **Room**: For local data persistence.
- **Flow & Coroutines**: For asynchronous operations and data handling within the DAO.

## Future Improvements

- **Cloud Backup**: Add Firebase integration to back up the shopping list to the cloud.
- **Dark Mode Support**: Implement a dark theme for better user experience in low light environments.
- **Sorting and Filtering**: Add more options for sorting and filtering the items.

## Credits

- Created by **Mingi Kang** as part of an academic assignment.

## License

This project is licensed under the MIT License - see the LICENSE file for details.


