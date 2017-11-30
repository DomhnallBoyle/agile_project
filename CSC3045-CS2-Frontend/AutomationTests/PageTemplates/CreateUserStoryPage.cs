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

        public Button LogOutButton
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

        public void enterCorrectStoryDetails(string storyName, string storyMarketValue, string storyDescription)
        {
            StoryNameTextBox.Text = storyName;
            StoryMarketValueTextBox.Text = storyMarketValue;
            StoryDescriptionTextBox.Text = storyDescription;

            CreateButton.Click();
        }

        public void enterEmptyNameStoryDetails( string storyMarketValue, string storyDescription)
        {
            StoryMarketValueTextBox.Text = storyMarketValue;
            StoryDescriptionTextBox.Text = storyDescription;

            CreateButton.Click();
        }

        public void enterEmptyDescriptionStoryDetails(string storyName, string storyMarketValue)
        {
            StoryNameTextBox.Text = storyName;
            StoryMarketValueTextBox.Text = storyMarketValue;

            CreateButton.Click();
        }

        public void enterEmptyMarketValueStoryDetails(string storyName, string storyDescription)
        {
            StoryNameTextBox.Text = storyName;
            StoryDescriptionTextBox.Text = storyDescription;

            CreateButton.Click();
        }
    }
}
