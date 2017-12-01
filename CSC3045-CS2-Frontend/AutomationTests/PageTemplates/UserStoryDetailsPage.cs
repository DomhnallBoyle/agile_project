using AutomationTests.Util;
using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WindowItems;
using TestStack.White.UIItems.WPFUIItems;

namespace AutomationTests.PageTemplates
{
    class UserStoryDetailsPage : BasePage
    {
        public Button CreateAcceptanceTestButton
        {
            get { return MainWindow.Get<Button>("CreateAcceptanceTestButton"); }
        }

        public ListBox UserStoryAcceptanceTests
        {
            get { return MainWindow.Get<ListBox>("UserStoryAcceptanceTests"); }
        }

        public Label StoryNameTextBlock
        {
            get { return MainWindow.Get<Label>("StoryNameTextBlock"); }
        }

        public CheckBox CompleteLabelBlock
        {
            get { return MainWindow.Get<CheckBox>("CompleteLabelBlock"); }
        }
     
        public UserStoryDetailsPage(Window window) : base(window)
        { }

        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("Acceptance Tests");
        }

    }


}
