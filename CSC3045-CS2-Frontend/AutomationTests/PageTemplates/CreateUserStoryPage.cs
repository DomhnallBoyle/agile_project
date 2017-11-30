using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests.PageTemplates
{
    class CreateUserStoryPage : BasePage
    {
        public CreateUserStoryPage(Window window) : base(window)
        { }

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

        public TextBox StoryNameTextBox
        {
            get { return MainWindow.Get<TextBox>("StoryNameTextBox"); }
        }

        public TextBox StoryMarketValueTextBox
        {
            get { return MainWindow.Get<TextBox>("StoryMarketValueTextBox"); }
        }

        public TextBox StoryDescriptionTextBox
        {
            get { return MainWindow.Get<TextBox>("StoryDescriptionTextBox"); }
        }

        public void enterStoryDetails(string storyName, string storyMarketValue, string storyDescription)
        {
            StoryNameTextBox.Text = storyName;
            StoryMarketValueTextBox.Text = storyMarketValue;
            StoryDescriptionTextBox.Text = storyDescription;

            CreateButton.Click();
        }
    }
}
