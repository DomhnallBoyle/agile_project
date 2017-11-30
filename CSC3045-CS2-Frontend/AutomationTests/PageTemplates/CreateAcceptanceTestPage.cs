using TestStack.White.UIItems;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests.PageTemplates
{
    public class CreateAcceptanceTestPage : BasePage
    {
    
        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("");
        }

        public Button CreateButton
        {
            get { return MainWindow.Get<Button>("CreateButton"); }
        }

        public Button CancelButton
        {
            get { return MainWindow.Get<Button>("CancelButton"); }
        }

        public TextBox GivenTextBox
        {
            get { return MainWindow.Get<TextBox>("GivenTextBox"); }
        }

        public TextBox WhenTextBox
        {
            get { return MainWindow.Get<TextBox>("WhenTextBox"); }
        }

        public TextBox ThenTextBox
        {
            get { return MainWindow.Get<TextBox>("ThenTextBox"); }
        }

        public void enterCorrectAcceptanceDetails(string given, string when, string then)
        {
            GivenTextBox.Text = given;
            whenTextBox.Text = when;
            thenTextBox.Text = then;

            CreateButton.Click();
        }

        public void enterEmptyGivenAcceptanceDetails(string when, string then)
        {
            whenTextBox.Text = when;
            thenTextBox.Text = then;

            CreateButton.Click();
        }

        public void enterEmptyWhenAcceptanceDetails(string given, string then)
        {
            GivenTextBox.Text = given;
            thenTextBox.Text = then;

            CreateButton.Click();
        }

        public void enterEmptyThenAcceptanceDetails(string given, string when)
        {
            GivenTextBox.Text = given;
            whenTextBox.Text = when;

            CreateButton.Click();
        }
    }

}
