using System.Windows;

namespace CSC3045_CS2.Utility
{
    public class MessageBoxUtil
    {
        public static void ShowInfoBox(string message)
        {
            MessageBox.Show(message, "Information", MessageBoxButton.OK, MessageBoxImage.Information);
        }

        public static void ShowSuccessBox(string message)
        {
            MessageBox.Show(message, "Success", MessageBoxButton.OK, MessageBoxImage.None);
        }

        public static void ShowErrorBox(string message)
        {
            MessageBox.Show(message, "Error", MessageBoxButton.OK, MessageBoxImage.Stop);
        }

        public static void ShowWarningBox(string message)
        {
            MessageBox.Show(message, "Warning", MessageBoxButton.OK, MessageBoxImage.Warning);
        }
    }
}
