using AutomationTests.Util;
using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WindowItems;
using TestStack.White.UIItems.WPFUIItems;

namespace AutomationTests.PageTemplates
{
    class UserStoryDetailsPage : BasePage
    {
        public UserStoryDetailsPage(Window window) : base(window)
        { }

        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("");
        }

        public Button CreateAcceptanceTestButton
        {
            get { return MainWindow.Get<Button>("CreateAcceptanceTestButton"); }
        }

        public Button BackButton
        {
            get { return MainWindow.Get<Button>("BackButton"); }
        }

        public ListBox UserStoryAcceptanceTests
        {
            get { return MainWindow.Get<ListBox>("UserStoryAcceptanceTests"); }
        }

        public Label StoryNameTextBlock
        {
            get { return MainWindow.Get<Label>("StoryNameTextBlock"); }
        }

        public CheckBox CompletedCheckBox
        {
            get { return MainWindow.Get<CheckBox>("CompletedCheckBox"); }
        }



    }
}
