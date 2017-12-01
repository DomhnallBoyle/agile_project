using TestStack.White.UIItems;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests.PageTemplates
{
    public class CreateSprintPage : BasePage
    {
        public TextBox SprintNameTextBox
        {
            get { return MainWindow.Get<TextBox>("SprintNameTextBox"); }
        }

        public WpfDatePicker StartDatePicker
        {
            get { return MainWindow.Get<WpfDatePicker>("StartDatePicker"); }
        }

        public WpfDatePicker EndDatePicker
        {
            get { return MainWindow.Get<WpfDatePicker>("EndDatePicker"); }
        }

        public Button CreateButton
        {
            get { return MainWindow.Get<Button>("CreateButton"); }
        }

        public CreateSprintPage(Window window) : base(window) { }

        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("Create Sprint");
        }
    }
}
