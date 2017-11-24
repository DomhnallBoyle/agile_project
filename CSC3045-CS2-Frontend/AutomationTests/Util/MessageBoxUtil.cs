using TestStack.White.UIItems;
using TestStack.White.UIItems.Finders;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests.Util
{
    public class MessageBoxUtil
    {
        public static string GetTextContent(Window messageBox)
        {
            return messageBox.Get<Label>(SearchCriteria.Indexed(0)).Text;
        }

        public static void ClickOKButton(Window messageBox)
        {
            messageBox.Get<Button>(SearchCriteria.Indexed(1)).Click();
        }

        public static Window GetInfoMessageBox(Window window)
        {
            return window.MessageBox("Information");
        }

        public static Window GetSuccessMessageBox(Window window)
        {
            return window.MessageBox("Success");
        }

        public static Window GetErrorMessageBox(Window window)
        {
            return window.MessageBox("Error");
        }

        public static Window GetWarningMessageBox(Window window)
        {
            return window.MessageBox("Warning");
        }

    }
}
